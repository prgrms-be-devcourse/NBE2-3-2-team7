package com.project.popupmarket.service.land;

import com.project.popupmarket.dto.land.RentalLandRespTO;
import com.project.popupmarket.dto.land.RentalLandTO;
import com.project.popupmarket.entity.RentalLand;
import com.project.popupmarket.enums.ActivateStatus;
import com.project.popupmarket.repository.RentalLandJpaRepository;
import com.project.popupmarket.service.aws.S3FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<RentalLandRespTO> findWithLimit() {
        ModelMapper modelMapper = new ModelMapper();

        return rentalLandJpaRepository.findWithLimit()
                .stream()
                .map(rp -> {
                    // RentalLandTO 매핑
                    RentalLandTO rentalLandTO = modelMapper.map(rp, RentalLandTO.class);

                    // 썸네일 URL 생성
                    String thumbnailFilePath = String.format("/land/%d_thumbnail.png", rp.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    // RentalLandRespTO 생성 및 데이터 설정
                    RentalLandRespTO respTO = new RentalLandRespTO();
                    respTO.setRentalLand(rentalLandTO);
                    respTO.setThumbnail(thumbnailUrl); // 썸네일만 포함된 리스트 설정
                    return respTO;
                })
                .toList();
    }

    public RentalLandTO findById(Long id) {
        return rentalLandJpaRepository.findById(id)
                .map(rentalLand -> new ModelMapper().map(rentalLand, RentalLandTO.class))
                .orElse(null);

    }

    public RentalLandRespTO getUserWithImages(Long id) {
        return rentalLandJpaRepository.findById(id)
                .map(rentalLand -> {
                    RentalLandTO rentalLandTO = new ModelMapper().map(rentalLand, RentalLandTO.class);

                    String filePath = String.format("land/%d_images_", rentalLand.getId());
                    String thumbnailFilePath = String.format("/land/%d_thumbnail.png", rentalLandTO.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

                    List<String> imageUrls = s3FileService.getCloudFrontImageListUrl(filePath);

                    RentalLandRespTO response = new RentalLandRespTO();
                    response.setRentalLand(rentalLandTO);
                    response.setThumbnail(thumbnailUrl);
                    response.setImages(imageUrls);

                    return response;
                })
                .orElse(null);
    }

    public Page<RentalLandRespTO> findFilteredWithPagination(
            Integer minCapacity, Integer maxCapacity, String location,
            BigDecimal minPrice, BigDecimal maxPrice,
            LocalDate startDate, LocalDate endDate,
            String sorting, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        return rentalLandJpaRepository
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
    }


    public List<RentalLandRespTO> findRentalPlacesByUserId(Long userSeq) {
        ModelMapper modelMapper = new ModelMapper();

        return rentalLandJpaRepository
                .findRentalPlacesByUserId(userSeq)
                .stream()
                .map(rp -> {
                    RentalLandTO rentalLandTO = modelMapper.map(rp, RentalLandTO.class);

                    String filePath = String.format("land/%d_images_", rentalLandTO.getId());
                    String thumbnailFilePath = String.format("/land/%d_thumbnail.png", rentalLandTO.getId());
                    String thumbnailUrl = s3FileService.getCloudFrontImageUrl(thumbnailFilePath);

//                    List<String> imageUrls = s3FileService.getCloudFrontImageListUrl(filePath);

                    RentalLandRespTO respTO = new RentalLandRespTO();
                    respTO.setRentalLand(rentalLandTO);
                    respTO.setThumbnail(thumbnailUrl);
//                    respTO.setImages(imageUrls);

                    return respTO;
                })
                .toList();
    }

    @Transactional
    public void insertRentalPlace(
            RentalLandTO to,
            MultipartFile thumbnail,
            List<MultipartFile> images) throws IOException {
        // RentalPlace 저장
        ModelMapper mapper = new ModelMapper();
        RentalLand rentalLand = mapper.map(to, RentalLand.class);
        rentalLand.setStatus(ActivateStatus.ACTIVE);

        RentalLand savedPlace = rentalLandJpaRepository.save(rentalLand);
        Long id = savedPlace.getId();

        s3FileService.uploadSingleImage(thumbnail, id, "land");
        s3FileService.uploadMultipleImages(images, id, "land");
    }

    @Transactional
    public void updateRentalPlaceStatus(Long id, String status) {
        try {
            ActivateStatus changedStatus = ActivateStatus.valueOf(status);

            rentalLandJpaRepository.updateStatusById(id, changedStatus.name());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteRentalImageById(Long id){
        s3FileService.deleteFiles("land", id);
    }

    @Transactional
    public void deleteRentalPlaceById(Long id){

        s3FileService.deleteFiles("land", id);
        rentalLandJpaRepository.deleteRentalPlaceById(id);
    }
}


