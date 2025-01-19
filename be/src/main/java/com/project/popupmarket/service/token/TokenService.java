package com.project.popupmarket.service.token;

import com.project.popupmarket.config.jwt.TokenProvider;
import com.project.popupmarket.entity.JwtToken;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.repository.JwtTokenRepository;
import com.project.popupmarket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserService userService;

    // 액세스 토큰 생성
    public String createAccessToken(User user) {
        // Access Token 생성 (2시간)
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }

    // 리프레시 토큰 생성
    public String  createRefreshToken(User user) {
        // 1. 기존 리프레시 토큰이 있다면 삭제
        jwtTokenRepository.findByUserId(user.getId())
                          .ifPresent(jwtTokenRepository::delete);

        // 2. TokenProvider를 사용하여 리프레시 토큰 생성
        // 예: 14일 유효기간 설정
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(14));

        // 3. DB에 리프레시 토큰 저장
        JwtToken jwtToken = JwtToken.builder()
                                    .userId(user.getId())
                                    .jwtToken(refreshToken)
                                    .build();

        jwtTokenRepository.save(jwtToken);

        return refreshToken;
    }

    // 리프레시 토큰으로 새로운 액세스 토큰 발급
    public String createNewAccessToken(String jwtToken) {
        if (!tokenProvider.validToken(jwtToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Long userId = findByJwtToken(jwtToken).getUserId();
        User user = userService.findById(userId);
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }

    // 사용자 ID로 Refesh 토큰 조회
    public JwtToken findByJwtToken(String jwtToken) {
        return jwtTokenRepository.findByJwtToken(jwtToken)
                                 .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

    //DB에서 JWT 토큰 삭제
    public void deleteJwtToken(String token) {
        jwtTokenRepository.deleteByJwtToken(token);
    }
}