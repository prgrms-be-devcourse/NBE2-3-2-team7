package com.project.popupmarket.controller.popup;

import com.project.popupmarket.dto.popup.PopupStoreImgDTO;
import com.project.popupmarket.dto.popup.PopupStoreRespTO;
import com.project.popupmarket.dto.popup.PopupStoreTO;
import com.project.popupmarket.repository.PopupStoreJpaRepository;
import com.project.popupmarket.service.popup.PopupStoreFileStorageService;
import com.project.popupmarket.service.popup.PopupStoreService;
import com.project.popupmarket.util.UserContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PopupStoreController {
    private final PopupStoreJpaRepository popupStoreJpaRepository;
    private final PopupStoreService popupStoreService;
    private final PopupStoreFileStorageService popupStoreFileStorageService;
    private final UserContextUtil userContextUtil;

    // [ CREATE ]
    @PostMapping(value = "/popup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "팝업스토어 추가")
    public ResponseEntity<String> createPopup(
            @RequestPart("popupStore") PopupStoreTO popupStore,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thimg,
            @RequestPart( "images") List<MultipartFile> images
    ) {
        long userSeq = userContextUtil.getUserId();
        popupStore.setCustomerId(userSeq);

        // 팝업, 썸네일, 상세 이미지
        boolean flag = popupStoreService.insert(popupStore, thimg, images);

        if (flag) {
            return new ResponseEntity<>("팝업스토어가 성공적으로 추가되었습니다.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("팝업스토어 추가에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }

    }

    // [ READ ] - 1
    // : 조건에 해당하는 팝업들 미리보기 - targetLocation, type, targetAgeGroup, startDate ~ endDate
    @GetMapping("/popup/list")
    @Operation(summary = "조건에 해당하는 팝업 리스트")
    public Page<PopupStoreRespTO> getPopupByFilter(
            @RequestParam(required = false) String targetLocation,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String targetAgeGroup,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) String sorting,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page,9);

        return popupStoreService.findByFilter(targetLocation, type, targetAgeGroup, startDate, endDate, sorting, pageable);
    }

    // [ READ ] - 2
    // 특정 번호에 해당하는 팝업스토어 상세 정보
    @GetMapping("/popup/{seq}")
    @Operation(summary = "개별 팝업 조회" )
    public PopupStoreRespTO  getPopupBySeq(@PathVariable Long seq) {
        PopupStoreRespTO to = popupStoreService.findBySeq(seq);

        if (to == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PopupStore not found with seq: " + seq);
        }
        return to;
    }

    // [ Read ] - 3 : 관리 중인 팝업스토어 목록
    // thumbnail, seq(팝업 기획자의 팝업 데이터), type, title, 입점 요청 몇 회 받았는지
    @GetMapping("/popup/user")
    @Operation(summary = "사용자 팝업 리스트")
    public List<PopupStoreRespTO> getPopupByUser() {
        Long userSeq = userContextUtil.getUserId();
        return popupStoreService.findByUserSeq(userSeq);
    }

    // [ Update ] : 개별 팝업 스토어 수정
    @PutMapping(value = "/popup/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "개별 팝업 수정")
    public ResponseEntity<String> updatePopup(
            @PathVariable Long id,
            @RequestPart(value = "popupStore", required = false) PopupStoreTO popupStore,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thimg,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        try {
            popupStore.setId(id);
            Long userSeq = userContextUtil.getUserId();
            popupStore.setCustomerId(userSeq);

            //delete 추가
            popupStoreService.deleteImage(id);
            popupStoreService.insert(popupStore, thimg, images);

            return ResponseEntity.ok("팝업 스토어 수정 성공");
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // [ Delete ]
    // 팝업 관리 페이지 "/mypage/popup/view?번호={팝업번호}" -> 해당 팝업에 대한 상세 정보와 삭제하기 버튼 같이 출력
    // 이때 삭제하기 버튼을 누르면 해당 요청 시행
    @DeleteMapping("/popup/{id}")
    @Operation(summary = "개별 팝업 삭제")
    public ResponseEntity<String> deletePopup(@PathVariable long id) {
        popupStoreService.delete(id);

        return ResponseEntity.ok("팝업이 성공적으로 삭제되었습니다.");
    }
}
