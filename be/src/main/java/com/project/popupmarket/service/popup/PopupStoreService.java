package com.project.popupmarket.service.popup;

import com.project.popupmarket.dto.popup.PopupStoreRespTO;
import com.project.popupmarket.dto.popup.PopupStoreTO;
import com.project.popupmarket.entity.PopupStore;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.exception.custom.ResourceNotFoundException;
import com.project.popupmarket.exception.custom.S3Exception;
import com.project.popupmarket.repository.PopupStoreJpaRepository;
import com.project.popupmarket.repository.UserRepository;
import com.project.popupmarket.service.aws.S3FileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

            User user = userRepository.findById(to.getCustomerId()).orElseThrow();
            popupStore.setCustomerId(user.getId()); // --> ??

            // 2. 팝업스토어 삽입
            PopupStore saved = popupStoreJpaRepository.save(popupStore);
            Long id = saved.getId();


            // 썸네일 업로드
            try {
                s3FileService.uploadSingleImage(thimg, id, "popup");
            } catch (Exception e) {
                throw new S3Exception(id + " : thumbnail 업로드에 실패했습니다.", e);
            }

            // 다중 이미지 업로드
            try {
                s3FileService.uploadMultipleImages(images, id, "popup");
            } catch (Exception e) {
                throw new S3Exception(id + " : images 업로드에 실패했습니다.", e);
            }

            return true;
        } catch (DataAccessException e) {
            throw new RuntimeException("데이터 베이스 등록에 실패했습니다. : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("오류가 발생했습니다. : " + e.getMessage(), e);
        }
    }

    // 2 - 1. Read : 특정 번호에 해당하는 팝업스토어 상세 정보
    public PopupStoreRespTO findBySeq(Long id) {
        // Repository에서 해당 데이터 찾기
//        PopupStore popupStore = popupStoreJpaRepository.findById(seq).orElse(null);
        return popupStoreJpaRepository.findById(id)
                .map( popupStore -> {
                    // 엔티티 -> TO 변환
                    PopupStoreTO popupStoreTO = new ModelMapper().map(popupStore, PopupStoreTO.class);

                    // S3 썸네일 URL 생성
                    String thumbnailFilePath = String.format("popup/%d_thumbnail.png", popupStore.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    // S3 이미지 리스트 URL 생성
                    String imageFilePathPrefix = String.format("popup/%d_images_", popupStore.getId());
                    List<String> imageUrls = s3FileService.getCloudFrontImageListUrl(imageFilePathPrefix);

                    // PopupStoreRespTO 생성 및 데이터 설정
                    PopupStoreRespTO popupStoreRespTO = new PopupStoreRespTO();
                    popupStoreRespTO.setPopupStoreTO(popupStoreTO);
                    popupStoreRespTO.setThumbnail(thumbnailUrl);
                    popupStoreRespTO.setImages(imageUrls);

                    return popupStoreRespTO;
                })
                .orElseThrow(() -> new ResourceNotFoundException("PopupStore not found with id:" + id));
    }

    // 2 - 2. Read : 조건에 해당하는 팝업 미리보기
    public Page<PopupStoreRespTO> findByFilter(String targetLocation, String type, String targetAgeGroup, LocalDate startDate, LocalDate endDate, String sorting, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();

        // 엔티티 -> TO로 매핑
        Page<PopupStoreRespTO> popupStoreRespTO =  popupStoreJpaRepository
                .findByFilter(targetLocation, type, targetAgeGroup, startDate, endDate, sorting, pageable)
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
                });

        if (popupStoreRespTO.isEmpty()) {
            // 데이터가 없을 경우 예외 발생
            throw new ResourceNotFoundException("팝업 스토어가 없습니다.");
        }

        return popupStoreRespTO;
    }

    // 2 - 3. Read : 사용자가 등록한 팝업 목록 보기
    public List<PopupStoreRespTO> findByUserSeq(Long userSeq) {
        ModelMapper modelMapper = new ModelMapper();

        List<PopupStoreRespTO> popupStoreRespTO =  popupStoreJpaRepository
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
        if (popupStoreRespTO.isEmpty()) {
            // 데이터가 없을 경우 예외 발생
            throw new ResourceNotFoundException("사용자의 팝업 스토어 리스트가 없습니다.");
        }
        return popupStoreRespTO;
    }

    // 2 - 4. Read : 메인 페이지 팝업 10개 조회
    public List<PopupStoreRespTO> findByLimit() {
        ModelMapper modelMapper = new ModelMapper();

        List<PopupStoreRespTO> popupStoreRespTO =  popupStoreJpaRepository
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
        if (popupStoreRespTO.isEmpty()) {
            // 데이터가 없을 경우 예외 발생
            throw new ResourceNotFoundException("팝업 스토어에 대한 데이터가 없습니다.");
        }

        return popupStoreRespTO;
    }

    // 4. Delete : 팝업리스트 삭제
    public void delete(long id) {

        // DB에서 삭제 대상 확인
        boolean exists = popupStoreJpaRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException(id + " 번호의 팝업 스토어 게시글이 존재하지 않습니다.");
        }

        // S3 파일 삭제
        try {
            s3FileService.deleteFiles("popup", id);
        } catch (Exception e) {
            throw new S3Exception(id + " 번호의 이미지 파일을 삭제하지 못했습니다.", e);
        }

        // DB에서 임대지 삭제
        popupStoreJpaRepository.deleteById(id);
    }
    // 4 - 1. Delete : 팝업 이미지 삭제
    public void deleteImage(long id) {

        try {
            s3FileService.deleteFiles("popup", id);
        } catch (Exception e) {
            throw new S3Exception(id + " 번호의 이미지 파일을 삭제하지 못했습니다.", e);
        }
    }
}
