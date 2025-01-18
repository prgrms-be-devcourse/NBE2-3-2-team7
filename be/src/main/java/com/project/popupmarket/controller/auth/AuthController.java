package com.project.popupmarket.controller.auth;

import com.project.popupmarket.dto.auth.LoginRequest;
import com.project.popupmarket.dto.token.CreateAccessTokenResponse;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.service.auth.AuthService;
import com.project.popupmarket.service.token.TokenService;
import com.project.popupmarket.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static com.project.popupmarket.config.handler.BaseAuthenticationSuccessHandler.JWT_TOKEN_COOKIE_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<CreateAccessTokenResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        // 1. 사용자 인증
        User authenticatedUser = authService.authenticate(request);

        // 2. 토큰 생성
        String accessToken = tokenService.createAccessTokens(authenticatedUser);
        String refreshToken = tokenService.createRefreshToken(authenticatedUser);

        // 3. 리프레시 토큰을 쿠키에 저장
        CookieUtil.addCookie(
                response,
                JWT_TOKEN_COOKIE_NAME,
                refreshToken,
                (int) Duration.ofDays(14).toSeconds()
                            );

        // 4. 액세스 토큰 반환
        return ResponseEntity.ok(new CreateAccessTokenResponse(accessToken));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        return CookieUtil.getCookie(request, JWT_TOKEN_COOKIE_NAME)
                         .map(cookie -> {
                             tokenService.deleteJwtToken(cookie.getValue());
                             CookieUtil.deleteCookie(request, response, JWT_TOKEN_COOKIE_NAME);
                             return ResponseEntity.ok().build();
                         })
                         .orElse(ResponseEntity.badRequest().build());
    }
}