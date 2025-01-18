package com.project.popupmarket.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class CookieUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    // 요청값(이름, 입력, 만료 기간을(초)) 입력 받아 쿠키 생성
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // Add security setting
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력 받아 삭제
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setHttpOnly(true); // Add security setting
                response.addCookie(cookie);
            }
        }
    }

    // 쿠키의 이름을 입력 받아 쿠키 반환
    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }

    // 객체를 직렬화해 쿠키의 값으로 변환
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    // 쿠키를 역지렬화해 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(cookie.getValue());
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            return objectMapper.readValue(decodedString, cls);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to deserialize cookie", e);
        }
    }
}