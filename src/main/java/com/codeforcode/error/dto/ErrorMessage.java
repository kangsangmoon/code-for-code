package com.codeforcode.error.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {
    //Server
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"예기치 못한 에러가 발생했습니다"),

    //User
    DUPLICATE_USERS_EXCEPTION(HttpStatus.CONFLICT, "중복된 유저가 존재합니다"),
    NOT_FOUND_USERS_EXCEPTION(HttpStatus.NOT_FOUND,"요청한 USER 를 찾을 수 없습니다"),
    INVALID_PASSWORD_REGEX(HttpStatus.CONFLICT, "유효한 비밀번호 형식이 아닙니다"),
    INVALID_EMAIL_REGEX(HttpStatus.CONFLICT,"유효한 이메일 형식이 아닙니다"),
    INVALID_NAME_REGEX(HttpStatus.CONFLICT,"유효한 이름 형식이 아닙니다"),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST,"ID/PW 가 일치하지 않습니다"),
    WRONG_ID(HttpStatus.BAD_REQUEST,"ID 가 일치하지 않습니다"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "Password 가 일치하지 않습니다"),

    //JWT
    INVALID_JWT(HttpStatus.UNAUTHORIZED,"유효하지 않은 JWT 토큰입니다"),


    //Redis
    REDIS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Redis Server에서 오류가 발생했습니다"),



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