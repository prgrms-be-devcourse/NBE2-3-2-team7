//package com.project.popupmarket.repository;
//
//import com.project.popupmarket.entity.RefreshToken;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.Optional;
//
//
//public interface JwtTokenRepository extends JpaRepository<RefreshToken, Long> {
//    Optional<RefreshToken> findByUserId(Long userId);
//    Optional<RefreshToken> findByJwtToken(String jwtToken);
//    void deleteByJwtToken(String token);
//}