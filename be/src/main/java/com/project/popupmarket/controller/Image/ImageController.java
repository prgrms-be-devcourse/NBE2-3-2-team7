package com.project.popupmarket.controller.Image;

import com.project.popupmarket.service.aws.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageController {
    @Autowired
    S3FileService s3FileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = s3FileService.uploadImage(file);
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @PostMapping("/single")
    public ResponseEntity<String> uploadSingleImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("category") String category,
            @RequestParam("userId") Long userId,
            @RequestParam("categoryId") Long categoryId
    ) {
        try {
            String imageUrl = s3FileService.uploadSingleImage(image, userId, categoryId, category);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<String>> uploadMultipleImages(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("category") String category,
            @RequestParam("userId") Long userId,
            @RequestParam("categoryId") Long categoryId
    ) {
        try {
            List<String> imageUrls = s3FileService.uploadMultipleImages(images, userId, categoryId, category);
            return ResponseEntity.ok(imageUrls);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(List.of("Image upload failed: " + e.getMessage()));
        }
    }

    @GetMapping("/images")
    public ResponseEntity<List<String>> getAllImageUrls() {
        List<String> imageUrls = s3FileService.getAllImageUrls();
        return ResponseEntity.ok(imageUrls);
    }


}
