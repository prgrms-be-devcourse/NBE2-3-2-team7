package com.project.popupmarket.repository;

import com.project.popupmarket.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByUserId(Long userId);
    Optional<JwtToken> findByJwtToken(String jwtToken);
    void deleteByJwtToken(String token);
}