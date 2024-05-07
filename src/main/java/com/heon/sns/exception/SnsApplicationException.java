package com.heon.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SnsApplicationException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    // errorCode만 있는 생성자
    public SnsApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        }
        return String.format("%s, %s", errorCode.getMessage(), message);
    }
}