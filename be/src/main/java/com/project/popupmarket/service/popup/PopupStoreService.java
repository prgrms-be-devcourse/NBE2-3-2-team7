package com.project.popupmarket.service.popup;

import com.project.popupmarket.dto.popup.PopupStoreRespTO;
import com.project.popupmarket.dto.popup.PopupStoreTO;
import com.project.popupmarket.entity.PopupStore;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.repository.PopupStoreJpaRepository;
import com.project.popupmarket.repository.UserRepository;
import com.project.popupmarket.service.aws.S3FileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupStoreService {
    @Autowired
    private S3FileService s3FileService;
    private final PopupStoreJpaRepository popupStoreJpaRepository;
//    private final PopupStoreImageJpaRepository popupStoreImageJpaRepository;
    private final PopupStoreFileStorageService popupStoreFileStorageService;
    private final UserRepository userRepository;

    // 1. Create : 팝업스토어 추가
    @Transactional
    public boolean insert(PopupStoreTO to, MultipartFile thimg, List<MultipartFile> images) {
        try {
            // 1. to -> 엔티티 매핑
            ModelMapper modelMapper = new ModelMapper();
            PopupStore popupStore = modelMapper.map(to, PopupStore.class);

//            User user = userRepository.findById(to.getCustomerId()).orElseThrow();
//            popupStore.setCustomerId(user.getId()); // --> ??

            // 2. 팝업스토어 삽입
            PopupStore saved = popupStoreJpaRepository.save(popupStore);
            Long id = saved.getId();

            s3FileService.uploadSingleImage(thimg, id, "popup");
            s3FileService.uploadMultipleImages(images, id, "popup");

            return true;
        } catch ( Exception e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }

    // 2 - 1. Read : 특정 번호에 해당하는 팝업스토어 상세 정보
    public PopupStoreRespTO findBySeq(Long seq) {
        // Repository에서 해당 데이터 찾기
        PopupStore popupStore = popupStoreJpaRepository.findById(seq).orElse(null);

        if (popupStore == null) {
            return null;
        }
        // 엔티티 -> TO 변환
        PopupStoreTO popupStoreTO = new ModelMapper().map(popupStore, PopupStoreTO.class);

        // S3 썸네일 URL 생성
        String thumbnailFilePath = String.format("popup/%d_thumbnail.png", popupStore.getId());
        String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

        // S3 이미지 리스트 URL 생성
        String imageFilePathPrefix = String.format("popup/%d_images_", popupStore.getId());
        List<String> imageUrls = s3FileService.getCloudFrontImageListUrl(imageFilePathPrefix);

        // PopupStoreRespTO 생성 및 데이터 설정
        PopupStoreRespTO respTO = new PopupStoreRespTO();
        respTO.setPopupStoreTO(popupStoreTO);
        respTO.setThumbnail(thumbnailUrl);
        respTO.setImages(imageUrls);

        return respTO;
    }

    // 2 - 2. Read : 조건에 해당하는 팝업 미리보기
    public Page<PopupStoreRespTO> findByFilter(String targetLocation, String type, String targetAgeGroup, LocalDate startDate, LocalDate endDate, String sorting, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();

        // 엔티티 -> TO로 매핑
        return popupStoreJpaRepository
                .findByFilter(targetLocation, type, targetAgeGroup, startDate, endDate, sorting, pageable)
                .map(p -> {
                    // 엔티티 -> TO 변환
                    PopupStoreTO popupStoreTO = modelMapper.map(p, PopupStoreTO.class);

                    // S3 썸네일 URL 생성
                    String thumbnailFilePath = String.format("popup/%d_thumbnail.png", p.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    // S3 이미지 리스트 URL 생성
                    String imageFilePathPrefix = String.format("popup/%d_images_", p.getId());
                    List<String> imageUrls = s3FileService.getCloudFrontImageListUrl(imageFilePathPrefix);

                    // PopupStoreRespTO 생성 및 데이터 설정
                    PopupStoreRespTO respTO = new PopupStoreRespTO();
                    respTO.setPopupStoreTO(popupStoreTO);
                    respTO.setThumbnail(thumbnailUrl);
                    respTO.setImages(imageUrls);

                    return respTO;
                });
    }

    // 2 - 3. Read : 사용자가 등록한 팝업 목록 보기
    public List<PopupStoreRespTO> findByUserSeq(Long userSeq) {
        ModelMapper modelMapper = new ModelMapper();

        return popupStoreJpaRepository
                .findByUserSeq(userSeq)
                .stream()
                .map(p -> {
                    // 엔티티 -> TO 변환
                    PopupStoreTO popupStoreTO = modelMapper.map(p, PopupStoreTO.class);

                    // S3 썸네일 URL 생성
                    String thumbnailFilePath = String.format("popup/%d_thumbnail.png", p.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    // PopupStoreRespTO 생성 및 데이터 설정
                    PopupStoreRespTO respTO = new PopupStoreRespTO();
                    respTO.setPopupStoreTO(popupStoreTO);
                    respTO.setThumbnail(thumbnailUrl);

                    return respTO;
                })
                .toList();
    }

    // 2 - 4. Read : 메인 페이지 팝업 10개 조회
    public List<PopupStoreRespTO> findByLimit() {
        ModelMapper modelMapper = new ModelMapper();

        return popupStoreJpaRepository
                .findByLimit()
                .stream()
                .map(p -> {
                    // 엔티티 -> TO 변환
                    PopupStoreTO popupStoreTO = modelMapper.map(p, PopupStoreTO.class);

                    // S3 썸네일 URL 생성
                    String thumbnailFilePath = String.format("popup/%d_thumbnail.png", p.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    // PopupStoreRespTO 생성 및 데이터 설정
                    PopupStoreRespTO respTO = new PopupStoreRespTO();
                    respTO.setPopupStoreTO(popupStoreTO);
                    respTO.setThumbnail(thumbnailUrl);

                    return respTO;
                })
                .toList();
    }

    // 4. Delete : 팝업리스트 삭제
    public boolean delete(long id) {
        try {
            s3FileService.deleteFiles("popup", id);
            popupStoreJpaRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            // 기타 예외 처리
            System.err.println("알 수 없는 오류로 삭제 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public void deleteImage(long id) {
        try {
            s3FileService.deleteFiles("popup", id);
        } catch (Exception e) {
            // 기타 예외 처리
            System.err.println("알 수 없는 오류로 삭제 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
