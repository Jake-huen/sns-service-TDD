package com.heon.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"),
    ENCORECT_PASSWORD(HttpStatus.CONFLICT, "Password is not correct");

    private HttpStatus status;
    private String message;
}
