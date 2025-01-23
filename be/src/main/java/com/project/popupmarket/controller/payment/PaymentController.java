package com.project.popupmarket.controller.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.popupmarket.dto.payment.*;
import com.project.popupmarket.service.receipts.PaymentService;
import com.project.popupmarket.service.receipts.TossRequestService;
import com.project.popupmarket.service.land.RentalLandService;
import com.project.popupmarket.util.UserContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;
    private final TossRequestService tossRequestService;
    private final UserContextUtil userContextUtil;
    private final RentalLandService rentalLandService;

    @Autowired
    public PaymentController(PaymentService paymentService, TossRequestService tossRequestService, UserContextUtil userContextUtil, RentalLandService rentalLandService) {
        this.paymentService = paymentService;
        this.tossRequestService = tossRequestService;
        this.userContextUtil = userContextUtil;
        this.rentalLandService = rentalLandService;
    }

    @GetMapping("/payment")
    @Operation(summary = "임대지 및 예약자 정보 조회")
    public ResponseEntity<ReservationInfoResponse> reservationInfo(
            @RequestParam Long seq,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end
    ) {
        ReservationTO reservation = new ReservationTO();

        reservation.setCustomerId(userContextUtil.getUserId());
        reservation.setRentalLandId(seq);
        reservation.setStartDate(start);
        reservation.setEndDate(end);

        ReservationInfoResponse resp = paymentService.getPaymentInfo(reservation);

        if (resp != null) {
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/payment")
    @Operation(summary = "임시 결제 내역 추가")
    public ResponseEntity<String> payment(@RequestBody ReceiptsTO receipt) {
        receipt.setCustomerId(userContextUtil.getUserId());

        boolean flag = paymentService.insertStagingPayment(receipt);

        if (flag) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(400).body("fail");
        }
    }

    @PostMapping("/payment/success")
    @Operation(summary = "결제 승인 요청 및 영수증 추가")
    public ResponseEntity<String> paymentSuccess(@RequestBody TossPaymentTO payment) throws JsonProcessingException {

        HttpResponse<String> response = tossRequestService.requestPayment(payment);

        ObjectMapper objectMapper = new ObjectMapper();

        if (response != null && response.statusCode() == 200) {
            ReceiptsTO receipt = objectMapper.readValue(response.body(), ReceiptsTO.class);
            receipt.setCustomerId(userContextUtil.getUserId());

            boolean flag = paymentService.insertReceipt(receipt);

            if (flag) {
                return ResponseEntity.ok("결제 성공");
            } else {
                HttpResponse<String> canceled = tossRequestService.cancelPayment(payment.getPaymentKey(), "시스템 에러로 인한 결제 취소");
                return ResponseEntity.status(500).body("결제 실패");
            }
        } else if (response != null){
            return ResponseEntity.status(response.statusCode())
                    .body("결제 실패");
        } else {
            return ResponseEntity.status(500)
                    .body("시스템 에러");
        }
    }

    @DeleteMapping("/payment/fail")
    @Operation(summary = "결제 실패, 임시 결제 내역 삭제")
    public ResponseEntity<String> paymentFail(
            @RequestBody ReceiptsTO receipt
    ) {
        boolean flag = paymentService.deleteStagingPayment(receipt);
        if (flag) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(500).body("fail");
        }
    }

    @GetMapping("/receipt")
    @Operation(summary = "사용자 결제 내역 리스트 조회")
    public List<ReceiptsInfoTO> receipt() {
        Long userId = userContextUtil.getUserId();

        return paymentService.getReceiptsByUserSeq(userId);
    }

    @GetMapping("/reservation/{rentalPlaceSeq}")
    @Operation(summary = "임대지 예약 리스트 조회")
    public ReservationResponse reservation(@PathVariable Long rentalPlaceSeq) {
        ReservationResponse resp = new ReservationResponse();
        resp.setRentalLandTitle(rentalLandService.findById(rentalPlaceSeq).getTitle());
        resp.setReservation(paymentService.getReceiptsByPlaceSeq(rentalPlaceSeq));

        return resp;
    }

    @PatchMapping("/receipt/{orderId}")
    @Operation(summary = "임대지 결제 환불")
    public ResponseEntity<String> receipt(
            @PathVariable String orderId
    ) {
        Long userId = userContextUtil.getUserId();

        TossPaymentTO payment = paymentService.changeReservationStatus(userId, orderId);

        if (payment != null) {
            HttpResponse<String> canceled = tossRequestService.cancelPayment(payment.getPaymentKey(), "시스템 에러로 인한 결제 취소");
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(500).body("fail");
        }
    }
}
