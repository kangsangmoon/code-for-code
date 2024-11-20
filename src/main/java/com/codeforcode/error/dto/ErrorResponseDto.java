package com.codeforcode.error.dto;

import com.codeforcode.error.BusinessException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {
    private String message;
    private final LocalDateTime serverDateTime;

    public ErrorResponseDto(String message) {
        this.message = message;
        this.serverDateTime = LocalDateTime.now();
    }

    public static ResponseEntity<ErrorResponseDto> of(ErrorMessage message) {
        return ResponseEntity
                .status(message.getStatus())
                .body(new ErrorResponseDto(message.name()));
    }
}