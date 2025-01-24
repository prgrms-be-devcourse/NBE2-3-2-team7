package com.project.popupmarket.service.token;

import com.project.popupmarket.config.jwt.TokenProvider;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Cacheable(value = "refreshToken", key = "#userId")
    public String findRefreshToken(Long userId) {
        return null;
    }

    public String createAccessToken(User user) {
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }

    @CachePut(value = "refreshToken", key = "#user.id")
    public String createRefreshToken(User user) {
        return tokenProvider.generateToken(user, Duration.ofDays(14));
    }

    public String createNewAccessToken(String refreshToken) {
        String savedToken = findRefreshToken(getUserIdFromToken(refreshToken));
        if (!savedToken.equals(refreshToken)) {
            throw new IllegalArgumentException("Invalid token");
        }
        Long userId = tokenProvider.getUserId(refreshToken);
        try {
            User user = userService.findById(userId);
            return createAccessToken(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found");
        }
    }

    @CacheEvict(value = "refreshToken", key = "#userId")
    public void deleteRefreshToken(Long userId) {
        // 캐시에서만 삭제되므로 추가 작업 필요 없음
    }

    // accessToken에서 userId 추출
    public Long getUserIdFromToken(String token) {
        return tokenProvider.getUserId(token);
    }
}