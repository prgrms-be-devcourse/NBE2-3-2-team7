package com.project.popupmarket.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 에러코드 필요시 추가.
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    LAND_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 임대지입니다."),
    CREDENTIAL_AUTH_ERROR(HttpStatus.NOT_FOUND, "인증 정보가 올바르지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
