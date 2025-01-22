package com.project.popupmarket.controller.recommendation;

import com.project.popupmarket.dto.recommendation.RecommendItemTO;
import com.project.popupmarket.dto.recommendation.PlaceDetailRespTO;
import com.project.popupmarket.dto.land.RentalLandTO;
import com.project.popupmarket.service.popup.PopupStoreService;
import com.project.popupmarket.service.receipts.PaymentService;
import com.project.popupmarket.service.land.RentalLandService;
import com.project.popupmarket.util.UserContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompositeController {

    private final RentalLandService rentalLandService;
    private final PopupStoreService popupStoreService;
//    private final PopupStoreImageJpaRepository popupStoreImageJpaRepository;
    private final UserContextUtil userContextUtil;
    private final PaymentService paymentService;

    @GetMapping("/main/new")
    @Operation(summary = "메인 페이지 최신 데이터 각각 10개 조회")
    public ResponseEntity<RecommendItemTO> getNewData() {
        return ResponseEntity.ok(new RecommendItemTO(popupStoreService.findByLimit(), rentalLandService.findWithLimit()));
    }

//    @GetMapping("/popup/view/{popupSeq}")
//    @Operation(summary = "개별 팝업 조회 (feat. 권유 목록)" )
//    public ResponseEntity<PopupDetailRespTO> getPopupBySeqWithPlaceInfo(@PathVariable Long popupSeq) {
//        Long userSeq = userContextUtil.getUserId();
//
//        PopupStoreTO to = popupStoreService.findBySeq(popupSeq);
////        List<String> imgLst = popupStoreImageJpaRepository.findById_PopupStoreSeq(popupSeq);
//
//        if (to == null) {
//            return ResponseEntity.status(404).build();
//        }
//        return ResponseEntity.ok(new PopupDetailRespTO(rentalLandService.findUserRentalPlaceInfo(userSeq, popupSeq), new PopupStoreImgDTO(to, imgLst)));
//    }

    @GetMapping("/land/view/{placeSeq}")
    @Operation(summary = "개별 임대지 조회 (feat. 예약 날짜)")
    public ResponseEntity<PlaceDetailRespTO> getPlaceBySeqWithReservationPeriod(@PathVariable Long placeSeq) {
        RentalLandTO to = rentalLandService.findById(placeSeq);
//        List<RentalPlaceImageTO> imageTo = rentalLandService.findRentalPlaceImageList(placeSeq);

        if (to == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(new PlaceDetailRespTO(paymentService.getRangeDates(placeSeq), null));
    }

    @GetMapping("/test/data")
    public ResponseEntity<Map<String, String>> getTestData() {
        Map<String, String> map = new HashMap<>();
        map.put("이름", "대호");
        map.put("나이", "25살");
        map.put("성별", "남성");
        map.put("직업", "무직");

        return ResponseEntity.ok(map);
    }
}
