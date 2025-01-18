package com.project.popupmarket.controller.mypage;

import com.project.popupmarket.dto.user.UserDto;
import com.project.popupmarket.dto.user.UserUpdateRequest;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final UserService userService;

    // 공통으로 사용할 사용자 정보 조회 메서드
    private UserDto getCurrentUser() {
        ModelMapper modelMapper = new ModelMapper();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Optional<User> user = userService.findByEmail(userEmail);

        if (user.isPresent()) {
//            userTO.setProfileImage(user.get().getProfileImage() != null ?
//                    user.get().getProfileImage() : defaultProfileImage);
            return modelMapper.map(user.get(), UserDto.class);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @GetMapping("/api/user")
    @ResponseBody
    public ResponseEntity<UserDto> getUserInfo() {
        try {
            return ResponseEntity.ok(getCurrentUser());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/api/updateUser")
    public ResponseEntity<String> updateUser(
            @RequestPart(value = "userUpdateRequest") UserUpdateRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        try {
            UserDto currentUser = getCurrentUser(); // 앞서 만든 getCurrentUser() 메서드 활용
            request.setProfileImage(profileImage);
            userService.updateUser(currentUser.getId(), request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/deleteUser")
    public ResponseEntity<String> deleteUser() {
        try {
            UserDto currentUser = getCurrentUser();
            userService.deleteUser(currentUser.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
