package com.project.popupmarket.service.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3FileService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String uploadImage(MultipartFile image) throws IOException {
        // 파일 이름 생성
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(image.getContentType())
                .build();

        // S3에 파일 업로드
        PutObjectResponse response = s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(image.getInputStream(), image.getSize())
        );

        return getImageUrl(fileName);
    }

    public List<String> getAllImageUrls() {
        // S3 버킷의 모든 객체 목록 가져오기
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucket)
                .build();

        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

        // 객체 키로부터 URL 생성
        return listObjectsResponse.contents().stream()
                .map(S3Object::key)
                .map(this::getImageUrl)
                .collect(Collectors.toList());
    }

    public String uploadSingleImage(MultipartFile image, Long userId, Long categoryId, String category) throws IOException {
        String key = String.format("%s/%d_%d_thumbnail_%s", category, userId, categoryId, image.getOriginalFilename());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(image.getContentType())
                .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(image.getInputStream(), image.getSize())
        );
        return getImageUrl(key);
    }

    public List<String> uploadMultipleImages(List<MultipartFile> images, Long userId, Long categoryId, String category) throws IOException {
        return images.stream()
                .map(image -> {
                    try {
                        int imageIndex = images.indexOf(image) + 1;
                        String key = String.format("%s/%d_%d_images_%d_%s", category, userId, categoryId, imageIndex, image.getOriginalFilename());

                        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                .bucket(bucket)
                                .key(key)
                                .contentType(image.getContentType())
                                .build();

                        s3Client.putObject(
                                putObjectRequest,
                                RequestBody.fromInputStream(image.getInputStream(), image.getSize())
                        );
                        return getImageUrl(key);
                    } catch (IOException e) {
                        throw new RuntimeException("Image upload failed: " + image.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());
    }

    public String getSingleImage(String category, Long userId, Long categoryId) {
        String prefix = String.format("%s/%d_%d_thumbnail_", category, userId, categoryId);

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(prefix)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .findFirst()
                .map(S3Object::key)
                .map(this::getImageUrl)
                .orElseThrow(() -> new RuntimeException("No single image found for userId: " + userId));
    }

    public List<String> getMultipleImages(String category, Long userId, Long categoryId) {
        String prefix = String.format("%s/%d_%d_images_", category, categoryId, userId);

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(prefix)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        // URL 리스트 생성
        return response.contents().stream()
                .map(S3Object::key)
                .map(this::getImageUrl)
                .collect(Collectors.toList());
    }

    public List<String> deleteFiles(String category, Long userId, Long categoryId) {
        String prefix = String.format("%s/%d_%d_", category, userId,categoryId);

        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(prefix)
                .build();

        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);

        List<String> keysToDelete = listResponse.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());

        // S3에서 각 파일 삭제
        keysToDelete.forEach(key -> {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteRequest);
        });

        return keysToDelete;
    }

    private String getImageUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, fileName);
    }
}
