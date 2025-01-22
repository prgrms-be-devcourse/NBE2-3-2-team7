package com.project.popupmarket.controller.auth;

import com.project.popupmarket.dto.oauth.OAuthSignupRequiredResponse;
import com.project.popupmarket.dto.token.CreateAccessTokenResponse;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.service.token.TokenService;
import com.project.popupmarket.service.user.UserService;
import com.project.popupmarket.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Optional;

import static com.project.popupmarket.config.jwt.AuthConstants.JWT_TOKEN_COOKIE_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class OAuth2Controller {

    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/oauth2/callback")
    public ResponseEntity<?> oAuth2Callback(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            HttpServletRequest ignoredRequest,
            HttpServletResponse response) {

        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            return ResponseEntity.badRequest().body("이메일 정보를 찾을 수 없습니다.");
        }

        try {
            // 기존 회원인지 확인
            Optional<User> userOptional = userService.findByEmail(email);

            if (userOptional.isEmpty()) {
                // 기존 회원이 아닌 경우
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body(new OAuthSignupRequiredResponse(
                                             "Google 로그인은 기존 회원만 사용할 수 있습니다. 회원가입을 먼저 진행해주세요.",
                                             email));
            }

            // 기존 회원인 경우 토큰 발급 및 처리
            User user = userOptional.get();
            String accessToken = tokenService.createAccessToken(user);
            String refreshToken = tokenService.createRefreshToken(user);

            // JWT 토큰을 쿠키에 저장
            CookieUtil.addCookie(
                    response,
                    JWT_TOKEN_COOKIE_NAME,
                    refreshToken,
                    (int) Duration
                            .ofDays(14).toSeconds()
                                );

            return ResponseEntity.ok()
                                 .body(new CreateAccessTokenResponse(accessToken));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("로그인 처리 중 오류가 발생했습니다.");
        }
    }
}
