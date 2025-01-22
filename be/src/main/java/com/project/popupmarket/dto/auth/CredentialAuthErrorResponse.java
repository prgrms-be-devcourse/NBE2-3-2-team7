package com.project.popupmarket.dto.auth;

import com.project.popupmarket.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class CredentialAuthErrorResponse {
    HttpStatus httpStatus = ErrorCode.CREDENTIAL_AUTH_ERROR.getStatus();
    String message = ErrorCode.CREDENTIAL_AUTH_ERROR.getMessage();
}
