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
    public boolean insert(PopupStoreTO to, String thimg, List<String> images) {
        try {
            // 1. to -> 엔티티 매핑
            ModelMapper modelMapper = new ModelMapper();
            PopupStore popupStore = modelMapper.map(to, PopupStore.class);
            User user = userRepository.findById(to.getCustomerId()).orElseThrow();
            popupStore.setCustomerId(user.getId());

            // 2. 팝업스토어 삽입
            popupStoreJpaRepository.save(popupStore);

            // 3. 팝업 TO 썸네일 파일 이름 설정
            // ex ) "파일 이름만."
//            popupStore.setThumbnail(thimg);

            // 4. 각 이미지에 대해 PopupStoreImageList 엔티티 생성
//            List<PopupStoreImageList> imageLists = images.stream()
//                    .map(image -> new PopupStoreImageList(new PopupStoreImageListId(popupStore.getId(), image)))
//                    .toList();

            // 5. 상세 이미지 DB 삽입
//            popupStoreImageJpaRepository.saveAll(imageLists);
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

    // 3 - 1 : Update PopupStore
    @Transactional
    public int updatePopup(Long seq, PopupStoreTO to, MultipartFile thumbnail) {
        // 1. 기존 팝업스토어 가져오기
        PopupStore existingPopupStore = popupStoreJpaRepository.findById(seq)
                .orElseThrow(() -> new EntityNotFoundException("PopupStore not found for ID: " + seq));

        // 2. 값 설정 : null이 아닌 값만 업데이트
        String type = (to.getType() != null && !to.getType().isBlank()) ? to.getType() : existingPopupStore.getType();
        String ageGroup = (to.getAgeGroup() != null && !to.getAgeGroup().isBlank()) ? to.getAgeGroup() : existingPopupStore.getAgeGroup().name();
//        String targetLocation = (to.getTargetLocation() != null && !to.getTargetLocation().isBlank()) ? to.getTargetLocation() : existingPopupStore.getTargetLocation();
        String title = (to.getTitle() != null && !to.getTitle().isBlank()) ? to.getTitle() : existingPopupStore.getTitle();
//        String wishArea = (to.getWishArea() != null) ? to.getWishArea() : existingPopupStore.getWishArea();
        String description = (to.getDescription() != null && !to.getDescription().isBlank()) ? to.getDescription() : existingPopupStore.getDescription();
        LocalDate startDate = (to.getStartDate() != null) ? to.getStartDate() : existingPopupStore.getStartDate();
        LocalDate endDate = (to.getEndDate() != null) ? to.getEndDate() : existingPopupStore.getEndDate();

        // 3. 썸네일 처리
//        if (thumbnail != null && !thumbnail.isEmpty()) {
////            String thPath = "src/main/resources/static/";
//            String thPath = "C:/popupmarket/popup_thumbnail/";
//            String existingThName = existingPopupStore.getThumbnail() == null ? "thumbnail_default.png" : existingPopupStore.getThumbnail();
//            System.out.println("매개변수로 받은 썸네일 있습니당.");
//
//            try {
//                // 기존 썸네일 삭제
//                if (existingThName != null) {
//                    String deleteThPath = thPath + existingThName;
//                    System.out.println("삭제 파일은 : " + deleteThPath);
//                    boolean deleted = popupStoreFileStorageService.deleteFile(deleteThPath);
//                    if (!deleted) {
//                        throw new RuntimeException("Failed to delete existing thumbnail: " + existingThName);
//                    }
//                }
//
//                // 새로운 썸네일을 기존 이름으로 저장
//                popupStoreFileStorageService.storeFile(thumbnail, thPath, existingThName);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to store thumbnail with name: " + existingThName, e);
//            }
//        }
//        else {
//            System.out.println("받은 썸네일 없음");
//        }

        // 4. 업데이트 쿼리 실행
        return popupStoreJpaRepository.updatePopupStore(
                seq, type, ageGroup, title, description, startDate, endDate
        );
    }

    // 3 - 2 : Update PopupStoreImageList
//    @Transactional
//    public int updatePopupImgs(Long seq, Long userSeq, List<MultipartFile> newImgs) {
//        // 1. 기존 팝업스토어 가져오기
//        PopupStore existingPopupStore = popupStoreJpaRepository.findById(seq)
//                .orElseThrow(() -> new EntityNotFoundException("PopupStore not found for ID: " + seq));
//
//
////        String imgPath = "src/main/resources/static/";
//        String imgPath = "C:/popupmarket/";
//
//        // 2. 기존 이미지 리스트 가져오기
//        List<String> existingImgs = popupStoreImageJpaRepository.findById_PopupStoreSeq(seq);
//
//        try {
//            // 3. 새 이미지 없다면 기존 이미지 유지
//            if (newImgs == null || newImgs.isEmpty()) {
//                return 0;
//            }
//
//            // 4. 새 이미지 있다면 기존 이미지 삭제
//            if (existingImgs != null && !existingImgs.isEmpty()) {
//                for (String img : existingImgs) {
//                    System.out.println("기존 이미지 이름 : " + img);
//                    String deleteImgPath = imgPath + "popup_detail/" + img;
//                    // 파일 경로에서 삭제
//                    boolean deleted = popupStoreFileStorageService.deleteFile(deleteImgPath);
//                    if (!deleted) {
//                        throw new RuntimeException("Failed to delete existing img: " + img);
//                    }
//                }
//                // DB에서 삭제
//                popupStoreImageJpaRepository.deleteAllById(seq);
//            }

            // 5. 새로운 이미지 저장
//            List<PopupStoreImageList> newImageEntities = new ArrayList<>();
//            int i = 1;
//            for (MultipartFile newImg : newImgs) {
//                if (!newImg.isEmpty()) {
//                    // 이미지 이름 지정
//                    String originalName = newImg.getOriginalFilename();
//                    String ext = originalName.substring(originalName.lastIndexOf(".") + 1);
//                    String newImgName = "popup_" + (seq) + "_images_" + i + "." + ext;
//
//                    // 파일 저장
//                    popupStoreFileStorageService.storeFile(newImg, imgPath + "popup_detail/", newImgName);
//
//                    // 이미지 엔티티 생성
//                    PopupStoreImageList newImage = new PopupStoreImageList();
//                    newImage.setId(new PopupStoreImageListId(seq, newImgName));
//                    newImageEntities.add(newImage);
//
//                    i++;
//                }
//            }
//
//            // 6. 새 이미지 레코드 삽입
//            popupStoreImageJpaRepository.saveAll(newImageEntities);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to update popup images for PopupStore ID: " + seq, e);
//        }
//        return 1;
//    }



    // 4. Delete : 팝업리스트 삭제
    public boolean delete(long seq) {
        try {
            // ID를 기반으로 팝업 스토어 삭제
//            List<String> imageNames = popupStoreImageJpaRepository.findById_PopupStoreSeq(seq);
//            String thumbnail = popupStoreJpaRepository.findById(seq).orElse(null).getThumbnail();
            popupStoreJpaRepository.deleteById(seq);

            // 이미지 경로 설정
//            String imgsPath = "C:/popupmarket/popup_detail";
//            String thPath = "C:/popupmarket/popup_thumbnail";

            // 팝업 이미지 삭제
//            for (String imageName : imageNames) {
//                deleteFilesInDirectory(imgsPath, imageName);
//            }
//
//            // 썸네일 이미지 삭제
//            if (thumbnail != null) {
//                deleteFilesInDirectory(thPath, thumbnail);
//            }

            return true;
        } catch (Exception e) {
            // 기타 예외 처리
            System.err.println("알 수 없는 오류로 삭제 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

//    private void deleteFilesInDirectory(String dirPath, String pattern) throws IOException {
//        Path path = Paths.get(dirPath);
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
//            for (Path entry : stream) {
//                File file = entry.toFile();
//                if (file.exists() && file.isFile()) {
//                    Files.delete(entry);  // 파일 삭제
//                    System.out.println("파일 삭제 성공: " + entry.toString());
//                } else {
//                    System.out.println("파일이 존재하지 않거나 잘못된 경로입니다: " + entry.toString());
//                }
//            }
//        }
//    }
}
