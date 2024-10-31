package com.codeforcode.error.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {
    //User
    DUPLICATE_USERS_EXCEPTION(HttpStatus.CONFLICT, "중복된 유저가 존재합니다"),
    NOT_FOUND_USERS_EXCEPTION(HttpStatus.NOT_FOUND,"요청한 USER 를 찾을 수 없습니다"),
    INVALID_PASSWORD_REGEX(HttpStatus.CONFLICT, "유효한 비밀번호 형식이 아닙니다"),
    INVALID_EMAIL_REGEX(HttpStatus.CONFLICT,"유효한 이메일 형식이 아닙니다"),

    ;


    private final HttpStatus status;
    private final String message;

    ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}