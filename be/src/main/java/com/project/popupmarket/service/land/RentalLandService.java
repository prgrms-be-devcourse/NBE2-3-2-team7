package com.project.popupmarket.service.land;

import com.project.popupmarket.dto.land.RentalLandRespTO;
import com.project.popupmarket.dto.land.RentalLandTO;
import com.project.popupmarket.entity.RentalLand;
import com.project.popupmarket.enums.ActivateStatus;
import com.project.popupmarket.exception.custom.ResourceNotFoundException;
import com.project.popupmarket.exception.custom.S3Exception;
import com.project.popupmarket.repository.RentalLandJpaRepository;
import com.project.popupmarket.service.aws.S3FileService;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalLandService {
    @Autowired
    private S3FileService s3FileService;

    private final RentalLandJpaRepository rentalLandJpaRepository;

    public RentalLandTO findById(Long id) {
        return rentalLandJpaRepository.findById(id)
                .map(rentalLand -> new ModelMapper().map(rentalLand, RentalLandTO.class))
                .orElseThrow(() -> new ResourceNotFoundException(id + "의 임대지 게시글이 없습니다."));
    }

    // 2 - 1. Read : 조건에 해당하는 팝업들 미리보기
    public Page<RentalLandRespTO> findFilteredWithPagination(
            Integer minCapacity, Integer maxCapacity, String location,
            BigDecimal minPrice, BigDecimal maxPrice,
            LocalDate startDate, LocalDate endDate,
            String sorting, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Page<RentalLandRespTO> rentalLandRespTO =  rentalLandJpaRepository
                .findFilteredWithPagination(minCapacity, maxCapacity, location, minPrice, maxPrice, startDate, endDate, sorting, pageable)
                .map(rp -> {
                    RentalLandTO rentalLandTO = modelMapper.map(rp, RentalLandTO.class);

                    String thumbnailFilePath = String.format("land/%d_thumbnail.png", rp.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    RentalLandRespTO respTO = new RentalLandRespTO();
                    respTO.setRentalLand(rentalLandTO);
                    respTO.setThumbnail(thumbnailUrl);

                    return respTO;
                });

        if (rentalLandRespTO.isEmpty()) {
            // 데이터가 없을 경우 예외 발생
            throw new ResourceNotFoundException("임대 가능한 공간이 없습니다.");
        }

        return rentalLandRespTO;
    }

    // 2 - 2. Read : 특정 번호에 해당하는 임대지 상세 정보
    public RentalLandRespTO getUserWithImages(Long id) {
        return rentalLandJpaRepository.findById(id)
                .map(rentalLand -> {
                    RentalLandTO rentalLandTO = new ModelMapper().map(rentalLand, RentalLandTO.class);

                    String filePath = String.format("land/%d_images_", rentalLand.getId());
                    String thumbnailFilePath = String.format("land/%d_thumbnail.png", rentalLandTO.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    List<String> imageUrls = s3FileService.getCloudFrontImageListUrl(filePath);

                    RentalLandRespTO response = new RentalLandRespTO();
                    response.setRentalLand(rentalLandTO);
                    response.setThumbnail(thumbnailUrl);
                    response.setImages(imageUrls);

                    return response;
                })
                .orElseThrow(() -> new ResourceNotFoundException(id + "의 임대지 게시글이 없습니다."));
    }

//  2 - 3. Read : 관리 중인 임대지 목록
    public List<RentalLandRespTO> findRentalPlacesByUserId(Long userSeq) {
        ModelMapper modelMapper = new ModelMapper();

        List<RentalLandRespTO> rentalLandRespTO = rentalLandJpaRepository
                .findRentalPlacesByUserId(userSeq)
                .stream()
                .map(rp -> {
                    RentalLandTO rentalLandTO = modelMapper.map(rp, RentalLandTO.class);

                    String thumbnailFilePath = String.format("land/%d_thumbnail.png", rentalLandTO.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    RentalLandRespTO respTO = new RentalLandRespTO();
                    respTO.setRentalLand(rentalLandTO);
                    respTO.setThumbnail(thumbnailUrl);

                    return respTO;
                })
                .toList();

        if (rentalLandRespTO.isEmpty()) {
            // 데이터가 없을 경우 예외 발생
            throw new ResourceNotFoundException("사용자의 임대지 리스트가 없습니다.");
        }
        return rentalLandRespTO;
    }

    // 2 - 4. Read : 메인 페이지 임대지 10개 조회
    public List<RentalLandRespTO> findWithLimit() {
        ModelMapper modelMapper = new ModelMapper();
        List<RentalLandRespTO> rentalLandRespTO=  rentalLandJpaRepository.findWithLimit()
                .stream()
                .map(rp -> {
                    // RentalLandTO 매핑
                    RentalLandTO rentalLandTO = modelMapper.map(rp, RentalLandTO.class);

                    // 썸네일 URL 생성
                    String thumbnailFilePath = String.format("land/%d_thumbnail.png", rp.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    // RentalLandRespTO 생성 및 데이터 설정
                    RentalLandRespTO respTO = new RentalLandRespTO();
                    respTO.setRentalLand(rentalLandTO);
                    respTO.setThumbnail(thumbnailUrl);
                    return respTO;
                })
                .toList();

        if (rentalLandRespTO.isEmpty()) {
            // 데이터가 없을 경우 예외 발생
            throw new ResourceNotFoundException("임대지에 대한 데이터가 없습니다.");
        }

        return rentalLandRespTO;
    }

    // 1. Create : 임대지 추가
    @Transactional
    public void insertRentalPlace(
            RentalLandTO to,
            MultipartFile thumbnail,
            List<MultipartFile> images) throws IOException {
        try {
            // RentalPlace 저장
            ModelMapper mapper = new ModelMapper();
            RentalLand rentalLand = mapper.map(to, RentalLand.class);
            rentalLand.setStatus(ActivateStatus.ACTIVE);

            RentalLand savedPlace = rentalLandJpaRepository.save(rentalLand);
            Long id = savedPlace.getId();

            // 썸네일 업로드
            try {
                s3FileService.uploadSingleImage(thumbnail, id, "land");
            } catch (Exception e) {
                throw new S3Exception(id + " : thumbnail 업로드에 실패했습니다.", e);
            }

            // 다중 이미지 업로드
            try {
                s3FileService.uploadMultipleImages(images, id, "land");
            } catch (Exception e) {
                throw new S3Exception(id + " : images 업로드에 실패했습니다.", e);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("데이터 베이스 등록에 실패했습니다. : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("오류가 발생했습니다. : " + e.getMessage(), e);
        }
    }

    // 3. Update : 임대지 상태 변경 -> [ACTIVE, INACTIVE]
    @Transactional
    public void updateRentalPlaceStatus(Long id, String status) {
        try {
            ActivateStatus changedStatus = ActivateStatus.valueOf(status);

            rentalLandJpaRepository.updateStatusById(id, changedStatus.name());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    // 4 - 1 . Delete : 임대지 이미지 삭제
    @Transactional
    public void deleteRentalPlaceById(Long id){

        // DB에서 삭제 대상 확인
        boolean exists = rentalLandJpaRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException(id + " 번호의 임대지 게시글이 존재하지 않습니다.");
        }

        // S3 파일 삭제
        try {
            s3FileService.deleteFiles("land", id);
        } catch (Exception e) {
            throw new S3Exception(id + " 번호의 이미지 파일을 삭제하지 못했습니다.", e);
        }

        // DB에서 임대지 삭제
        rentalLandJpaRepository.deleteRentalPlaceById(id);
    }

    // 4 - 2. Delete : 임대지 이미지 삭제
    @Transactional
    public void deleteRentalImageById(Long id){
        try {
            s3FileService.deleteFiles("land", id);
        } catch (Exception e) {
            throw new S3Exception(id + " 번호의 이미지 파일을 삭제하지 못했습니다.", e);
        }
    }
}


