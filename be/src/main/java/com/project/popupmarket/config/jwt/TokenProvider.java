package com.project.popupmarket.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import com.project.popupmarket.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
                // 내용 iss : Yubanghyeon@gmail.com properties에서 설정한 값
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) // 내용 iat : 토큰 발급 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변수값
                .setSubject(user.getEmail()) // 내용 sub : 사용자 이메일
                .claim("id", user.getId()) // 내용 id : 사용자 id
                // 서명 : 비밀값과 함께 HS256 알고리즘 사용
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    //jwt 토큰 유효성 검사
    public boolean validToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
                .build()
                .parseClaimsJws(token);

            return true;
        } catch (Exception e) { // 문제가 발생하면 false 반환
            return false;
        }
    }
    // jwt 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    // jwt 토큰에서 유저ID를 가져오는 메소드
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // jwt 토큰에서 유저ID 객체를 가져오는 메소드
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}