package com.project.popupmarket.controller.land;

import com.project.popupmarket.dto.land.RentalLandRespTO;
import com.project.popupmarket.dto.land.RentalLandTO;
import com.project.popupmarket.service.land.RentalLandService;
import com.project.popupmarket.util.UserContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class RentalPlaceController {
    @Autowired
    private RentalLandService rentalLandService;
    @Autowired
    private UserContextUtil userContextUtil;

    @GetMapping("/rental/list")
    @Operation(summary = "조건에 해당하는 임대지 리스트")
    public Page<RentalLandRespTO> rentalListPagination( // 임대 리스트 페이지 9개 + 필터링 + 페이지네이션
                                                    @RequestParam(required = false) Integer minCapacity, // 최소 면적 기본값 0
                                                    @RequestParam(required = false) Integer maxCapacity, // 최소 면적 기본값 100
                                                    @RequestParam(required = false) String location,     // 위치, 기본값 null
                                                    @RequestParam(required = false) BigDecimal minPrice, // 최소 가격 기본값 0
                                                    @RequestParam(required = false) BigDecimal maxPrice, // 최소 가격 기본값 10000000
                                                    @RequestParam(required = false) LocalDate startDate, // 시작일
                                                    @RequestParam(required = false) LocalDate endDate,   // 종료일
                                                    @RequestParam(required = false) String sorting,      // 정렬 기준
                                                    @RequestParam(defaultValue = "0") int page
    ) {
//        GET /list?page=0 -> 초기 값
//        GET /list?minArea=30&maxArea=70&location=서울&minPrice=100000&maxPrice=9000000&page=0
        //Capacity, Price, Name, Thumbnail

        minCapacity = (minCapacity != null) ? minCapacity : 1;
        maxCapacity = (maxCapacity != null) ? maxCapacity : 500;
        minPrice = (minPrice != null) ? minPrice : new BigDecimal(1);
        maxPrice = (maxPrice != null) ? maxPrice : new BigDecimal(10000000);

        Pageable pageable = PageRequest.of(page, 9);

        return rentalLandService.findFilteredWithPagination(minCapacity, maxCapacity, location, minPrice, maxPrice, startDate, endDate, sorting, pageable);
    }

    @GetMapping("/rental/user")
    @Operation(summary = "사용자 임대지 리스트")
    public List<RentalLandRespTO> userRentalList() {
        Long userSeq = userContextUtil.getUserId();

        return rentalLandService.findRentalPlacesByUserId(userSeq);
    }

    @PatchMapping("/rental/{id}")
    @Operation(summary = "임대지 상태 변경 -> [ACTIVE, INACTIVE]")
    public ResponseEntity<Void> updateRentalStatus(
            @PathVariable("id") Long id,
            @RequestBody String status
    ) {
        rentalLandService.updateRentalPlaceStatus(id, status);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "임대지 추가")
    @PostMapping(value = "/rental", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> insertRentalPlace( // 임대페이지 데이터 create
            @RequestPart("rentalPlace") RentalLandTO rentalPlace,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart("images") List<MultipartFile> images
    ) {
        Long userSeq = userContextUtil.getUserId();

        rentalPlace.setLandlordId(userSeq);

//        rentalLandService.insertRentalPlaceWithImages(rentalPlace, thumbnail, images);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/rental/{id}")
    @Operation(summary = "개별 임대지 조회")
    public ResponseEntity<RentalLandRespTO> rentalPlaceById(
            @PathVariable Long id
    ) {
        RentalLandRespTO to = rentalLandService.getUserWithImages(id);

        if (to == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(null);
    }

    @PutMapping("/rental/{id}")
    @Operation(summary = "개별 임대지 수정")
    public ResponseEntity<Void> updateRentalPlace(
            @PathVariable("id") Long id,
            @RequestPart("rentalPlace") RentalLandTO rentalPlaceTO,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart("images") List<MultipartFile> images
    ) {

        rentalPlaceTO.setId(id);
        rentalPlaceTO.setLandlordId(userContextUtil.getUserId());

        rentalLandService.insertRentalPlace(rentalPlaceTO, thumbnail);
//        rentalLandService.deleteRentalPlaceImageById(id);
//        rentalLandService.updateRentalPlaceImage(id,images);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/rental/{id}")
    @Operation(summary = "개별 임대지 삭제")
    public ResponseEntity<Void> deleteRentalPlace(
            @PathVariable Long id
    ) {
        rentalLandService.deleteRentalPlaceById(id);

        return ResponseEntity.noContent().build(); // 상태 코드 204 반환
    }


}
