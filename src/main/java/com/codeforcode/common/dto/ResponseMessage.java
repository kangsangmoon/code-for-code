package com.codeforcode.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseMessage {

    SUCCESS(HttpStatus.OK,"SUCCESS"),


    //User
    SUCCESS_SIGN_UP_EMAIL_CHECK(HttpStatus.OK, "중복되지 않은 이메일"),
    CREATE_SUCCESS_USER(HttpStatus.CREATED, "회원 회원 가입 성공"),
    SUCCESS_LOAD_USER_INFORMATION(HttpStatus.OK, "회원 정보 조회 성공"),
    SUCCESS_SEARCH_ALL_USER(HttpStatus.OK, "모든 회원 조회 성공"),
    SUCCESS_UPDATE_USER(HttpStatus.OK, "회원 정보 변경 성공"),
    SUCCESS_USER_INFO_FETCH(HttpStatus.OK, "토큰을 통한 회원 정보 가져오기 성공"),


    //BOARD
    CREATE_SUCCESS_BOARD(HttpStatus.CREATED,"Board를 생성 했습니다."),
    UPDATE_SUCCESS_BOARD(HttpStatus.OK,"Board를 수정 하였습니다."),
    READ_SUCCESS_BOARD(HttpStatus.OK,"해당 Board 조회에 성공했습니다."),
    READ_SUCCESS_ALL_BOARD(HttpStatus.OK,"전체 Board 조회를 성공 했습니다."),
    //READ_SUCCESS_BOARD_CATEGORY(HttpStatus.OK,"Board Category 조회를 성공 했습니다."),

    READ_SUCCESS_BOARD_VIEW_COUNT(HttpStatus.OK,"해당 Board 조회수 조회를 성공 했습니다."),
    DELETE_SUCCESS_BOARD(HttpStatus.OK,"해당 Board 삭제 성공 했습니다."),
    //UPLOAD_IMAGE_SUCCESS(HttpStatus.CREATED, "이미지 업로드 성공"),

    //SOLUTION
    CREATE_SUCCESS_SOLUTION(HttpStatus.CREATED,"문제 생성 성공 했습니다."),
    CREATE_FAIL_SOLUTION(HttpStatus.BAD_REQUEST,"문제 생성 실패했습니다"),
    UPDATE_SUCCESS_SOLUTION(HttpStatus.OK,"문제 수정 성공 했습니다."),
    READ_SUCCESS_SOLUTION(HttpStatus.OK, "Solution 조회 성공했습니다"),
    READ_ALL_SUCCESS_SOLUTION(HttpStatus.OK,"전체 문제 읽기 성공 했습니다."),
    DELETE_SUCCESS_SOLUTION(HttpStatus.OK,"해당 문제 삭제 성공 했습니다."),
    DELETE_FAIL_SOLUTION(HttpStatus.NOT_FOUND, "해당 문제를 삭제하는데 실패했습니다"),

    //Compile
    COMPILE_SUCCESS(HttpStatus.OK, "컴파일 및 실행 성공"),
    INVALID_LANGUAGE(HttpStatus.BAD_REQUEST, "잘못된 언어 요청"),
    IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "입출력 오류 발생"),
    EXECUTION_INTERRUPTED(HttpStatus.INTERNAL_SERVER_ERROR, "코드 실행 중단됨"),
    GENERAL_COMPILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "컴파일 과정에서 일반 오류 발생"),
    ;

    public final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String NOT_FOUND_MESSAGE = "NOT FOUND";


    private final HttpStatus status;
    private final String message;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}