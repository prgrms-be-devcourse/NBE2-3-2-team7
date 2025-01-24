package com.project.popupmarket.config.jwt;

import java.time.Duration;

public final class AuthConstants {
    // 생성자를 private으로 선언하여 인스턴스화 방지
    private AuthConstants() {}

    public static final String JWT_TOKEN_COOKIE_NAME = "jwt_token";
    public static final Duration JWT_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

}
