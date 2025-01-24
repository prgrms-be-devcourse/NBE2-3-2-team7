package com.project.popupmarket.controller.user;

import com.project.popupmarket.dto.token.CreateAccessTokenResponse;
import com.project.popupmarket.dto.user.UserRegisterDto;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.service.token.TokenService;
import com.project.popupmarket.service.user.UserService;
import com.project.popupmarket.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static com.project.popupmarket.config.jwt.AuthConstants.JWT_TOKEN_COOKIE_NAME;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApiController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegisterDto request, HttpServletResponse response) {
        // 회원 저장 및 ID 반환
        Long userId = userService.save(request);

        // 저장된 회원 정보 조회
        User user = userService.findById(userId);

        // JWT 토큰 생성
        String refreshToken = tokenService.createRefreshToken(user);
        String accessToken = tokenService.createAccessToken(user);


        // 쿠키에 리프레시 토큰 저장
        CookieUtil.addCookie(response, JWT_TOKEN_COOKIE_NAME, refreshToken, (int) Duration
                .ofDays(14)
                .toSeconds());

        return ResponseEntity.ok(new CreateAccessTokenResponse(accessToken));
    }

}
