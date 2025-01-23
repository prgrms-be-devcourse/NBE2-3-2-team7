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

//    public String findPlaceThumbnailById(Long placeSeq) {
//        return rentalPlaceJpaRepository.findById(placeSeq).get().getThumbnail();
//    }

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

//    public List<RentalPlaceImageTO> findRentalPlaceImageList (Long id) {
//
//        List<RentalPlaceImageList> lists = rentalPlaceImageListJpaRepository.findRentalPlaceImageList(id);
//
//        List<RentalPlaceImageTO> toList = new ArrayList<>();
//        for (RentalPlaceImageList result : lists) {
//            RentalPlaceImageTO to = new RentalPlaceImageTO();
//            to.setRentalPlaceSeq(result.getId().getRentalPlaceSeq());
//            to.setImage(result.getId().getImage());
//            toList.add(to);
//        }
//
//        return toList;
//    }

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
    public void insertRentalPlace(RentalLandTO to, MultipartFile thumbnail){
        ModelMapper mapper = new ModelMapper();
        RentalLand rentalLand = mapper.map(to, RentalLand.class);
        rentalLand.setStatus(ActivateStatus.ACTIVE);

        RentalLand savedPlace = rentalLandJpaRepository.save(rentalLand);

        if (savedPlace.getId() == null) throw new RuntimeException();

//        Long id = savedPlace.getId();
//        Long userSeq = savedPlace.getLandlordId();
//        String thumbnailName;
//        if (thumbnail == null || thumbnail.isEmpty()) {
//            savedPlace.setThumbnail(null);
//        } else {
//            String extension = "";
//            String originalFilename = thumbnail.getOriginalFilename();
//            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//            thumbnailName = String.format("place_%d_%d_thumbnail.%s", id, userSeq, extension);
//            saveFile(thumbnail, "place_thumbnail", thumbnailName); // 파일 저장
//            savedPlace.setThumbnail(thumbnailName);
//        }
//        RentalLand saved = rentalPlaceJpaRepository.save(savedPlace);
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

//    @Transactional
//    public void insertRentalPlaceWithImages(
//            RentalPlaceTO to,
//            MultipartFile thumbnail,
//            List<MultipartFile> images){
//        // RentalPlace 저장
//        ModelMapper mapper = new ModelMapper();
//        RentalLand rentalLand = mapper.map(to, RentalLand.class);
//        rentalLand.setStatus(ActivateStatus.ACTIVE);
//
//        RentalLand savedPlace = rentalPlaceJpaRepository.save(rentalLand);
//        Long id = savedPlace.getId();
//        Long userSeq = savedPlace.getLandlordId();

//        // 썸네일 파일 저장
//        String thumbnailName;
//        if (thumbnail == null || thumbnail.isEmpty()) {
//            thumbnailName = "thumbnail_default.png";
//            savedPlace.setThumbnail(thumbnailName);
//        } else {
//            String extension = "";
//            String originalFilename = thumbnail.getOriginalFilename();
//            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//            thumbnailName = String.format("place_%d_%d_thumbnail.%s", id, userSeq, extension);
//            saveFile(thumbnail, "place_thumbnail", thumbnailName); // 파일 저장
//            savedPlace.setThumbnail(thumbnailName);
//        }
//        rentalPlaceJpaRepository.save(savedPlace);

        // 상세 이미지 저장
//        List<RentalPlaceImageList> lists = new ArrayList<>();
//        if (!images.isEmpty()) {
//            for (int i = 0; i < images.size(); i++) {
//                MultipartFile imageFile = images.get(i);
//
//                String extension = "";
//                String originalFilename = imageFile.getOriginalFilename();
//                extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//                String imageName = String.format("place_%d_%d_images_%d.%s", id, userSeq, i + 1, extension);
//                saveFile(imageFile, "place_detail", imageName);
//
//                RentalPlaceImageListId imageListId = new RentalPlaceImageListId();
//                imageListId.setRentalPlaceSeq(id);
//                imageListId.setImage(imageName);
//
//                RentalPlaceImageList imageList = new RentalPlaceImageList();
//                imageList.setId(imageListId);
//                imageList.setRentalPlaceSeq(savedPlace);
//
//                lists.add(imageList);
//            }
//
//            rentalPlaceImageListJpaRepository.saveAll(lists);
//        } else {
//            // 이미지가 비어있을 때 기본 이미지 추가 / 추가 x 상세 이미지는 무조건 들어와야 됨
//            String defaultImageName = "thumbnail_default.png";
//
//            RentalPlaceImageListId imageListId = new RentalPlaceImageListId();
//            imageListId.setRentalPlaceSeq(id);
//            imageListId.setImage(defaultImageName);
//
//            RentalPlaceImageList imageList = new RentalPlaceImageList();
//            imageList.setId(imageListId);
//            imageList.setRentalPlaceSeq(savedPlace);
//
//            lists.add(imageList);
//        }
//    }

//    @Transactional
//    public void updateRentalPlaceImage(Long id, List<MultipartFile> images){
//        RentalLand savedPlace = rentalPlaceJpaRepository.findById(id).orElseThrow();
//        Long userSeq = savedPlace.getLandlordId();
//
//        // 이미지 데이터 저장
//        List<RentalPlaceImageList> lists = new ArrayList<>();
//        if (images == null || images.isEmpty()) {
//            // 이미지가 없을 때 기본 이미지 추가 -> null로 적용.
//            String defaultImageName = "thumbnail_default.png";
//
//            RentalPlaceImageListId imageListId = new RentalPlaceImageListId();
//            imageListId.setRentalPlaceSeq(id);
//            imageListId.setImage(defaultImageName);
//
//            RentalPlaceImageList imageList = new RentalPlaceImageList();
//            imageList.setId(imageListId);
//            imageList.setRentalPlaceSeq(savedPlace);
//
//            lists.add(imageList);
//        } else {
//            // 이미지가 있을 때 처리
//            for (int i = 0; i < images.size(); i++) {
//
//                String imageName = String.format("rental_%d_%d_images_%d.png", id, userSeq, i + 1);
//
//                saveFile(images.get(i), "place_detail", imageName);
//
//                RentalPlaceImageListId imageListId = new RentalPlaceImageListId();
//                imageListId.setRentalPlaceSeq(id);
//                imageListId.setImage(imageName);
//
//                RentalPlaceImageList imageList = new RentalPlaceImageList();
//                imageList.setId(imageListId);
//                imageList.setRentalPlaceSeq(savedPlace);
//
//                lists.add(imageList);
//            }
//        }
//
//        rentalPlaceImageListJpaRepository.saveAll(lists);
//    }

    @Transactional
    public void deleteRentalPlaceById(Long id){
        Long userSeq = rentalLandJpaRepository.findUserSeqById(id);

//        deleteThumbnailFile(id, userSeq);
//        deleteImageFiles(id, userSeq);

//        placeRequestRepository.deletePlaceRequestsByRentalPlaceSeq(id);
//        rentalPlaceImageListJpaRepository.deleteRentalPlaceImageBySeq(id);
        rentalLandJpaRepository.deleteRentalPlaceById(id);

    }

//    @Transactional
//    public int deleteRentalPlaceImageById(Long id){
//        int flag=0;
//        rentalPlaceImageListJpaRepository.deleteRentalPlaceImageBySeq(id);
//
//        return flag;
//    }
//
//    private void saveFile(MultipartFile file, String folder, String filename) {
//        String path = "C:/popupmarket/";
//        try {
//            String uploadDir = path + folder + "/";
//            Path filePath = Paths.get(uploadDir + filename);
//            Files.createDirectories(filePath.getParent());
//            Files.write(filePath, file.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException("파일 저장 실패", e);
//        }
//    }

//    private void deleteThumbnailFile(Long id, Long userSeq) {
//        try {
//            String basePath = "C:/popupmarket/";
//            String thumbnailPath = String.format(basePath + "place_thumbnail/place_%d_%d_thumbnail.png", id, userSeq);
//            File thumbnailFile = new File(thumbnailPath);
//
//            if (thumbnailFile.exists()) {
//                if (thumbnailFile.delete()) {
//                    System.out.println("썸네일 파일 삭제 성공: " + thumbnailPath);
//                } else {
//                    System.err.println("썸네일 파일 삭제 실패: " + thumbnailPath);
//                }
//            } else {
//                System.out.println("삭제할 썸네일 파일이 존재하지 않음: " + thumbnailPath);
//            }
//        } catch (Exception e) {
//            System.err.println("썸네일 파일 삭제 중 오류 발생: " + e.getMessage());
//        }
//    }
//    private void deleteImageFiles(Long id, Long userSeq) {
//        try {
//            String basePath = "C:/popupmarket/";
//            String imagePathPattern = String.format(basePath + "place_detail/place_%d_%d_images_", id, userSeq);
//
//            int index = 1;
//            while (true) {
//                String imagePath = String.format(imagePathPattern + "%d.png", index);
//                File imageFile = new File(imagePath);
//
//                if (imageFile.exists()) {
//                    if (imageFile.delete()) {
//                        System.out.println("상세 이미지 파일 삭제 성공: " + imagePath);
//                    } else {
//                        System.err.println("상세 이미지 파일 삭제 실패: " + imagePath);
//                    }
//                } else {
//                    System.out.println("삭제할 이미지 파일이 더 이상 존재하지 않음 (index: " + index + ")");
//                    break;
//                }
//                index++;
//            }
//        } catch (Exception e) {
//            System.err.println("상세 이미지 파일 삭제 중 오류 발생: " + e.getMessage());
//        }
//    }

}
