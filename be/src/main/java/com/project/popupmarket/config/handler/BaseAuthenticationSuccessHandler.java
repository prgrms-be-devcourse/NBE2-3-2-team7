package com.project.popupmarket.config.handler;

import com.project.popupmarket.config.jwt.TokenProvider;
import com.project.popupmarket.entity.JwtToken;
import com.project.popupmarket.repository.JwtTokenRepository;
import com.project.popupmarket.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

@RequiredArgsConstructor
public abstract class BaseAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String JWT_TOKEN_COOKIE_NAME = "jwt_token";
    public static final Duration JWT_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/main";

    protected final TokenProvider tokenProvider;
    protected final JwtTokenRepository jwtTokenRepository;

    protected void saveJwtToken(Long userId, String newJwtToken) {
        JwtToken jwtToken = jwtTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newJwtToken))
                .orElse(JwtToken.builder()
                        .userId(userId)
                        .jwtToken(newJwtToken)
                        .build());

        jwtTokenRepository.save(jwtToken);
    }

    protected void addJwtTokenToCookie(HttpServletRequest request, HttpServletResponse response, String jwtToken) {
        int cookieMaxAge = (int) JWT_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, JWT_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, JWT_TOKEN_COOKIE_NAME, jwtToken, cookieMaxAge);
    }

    protected String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }
}

