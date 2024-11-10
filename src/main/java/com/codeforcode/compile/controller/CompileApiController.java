package com.codeforcode.compile.controller;


import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.common.dto.ResponseDto;
import com.codeforcode.common.dto.ResponseMessage;
import com.codeforcode.compile.dto.CompileRequest;
import com.codeforcode.compile.service.CompileService;
import com.codeforcode.error.dto.ErrorMessage;
import com.codeforcode.error.dto.ErrorResponseDto;
import com.codeforcode.example.domain.Example;
import com.codeforcode.example.service.ExampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compile")
public class CompileApiController {

    private final CompileService compileService;
    private final ExampleService exampleService;
    private final TokenProvider tokenProvider;  // TokenProvider 주입

    @PostMapping("/{solutionId}")
    public ResponseEntity<?> compileCode(
            @PathVariable Long solutionId,
            @RequestBody CompileRequest request,
            @RequestHeader("Authorization") String token) throws IOException, InterruptedException {

        if (!tokenProvider.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }

        Example example = exampleService.getExampleBySolutionId(solutionId);
        if (example == null) {
            log.warn("문제에 맞는 예제를 찾지 못함: {}", solutionId);
            return ErrorResponseDto.of(ErrorMessage.EXAMPLE_NOT_FIND_BY_ID);
        }

        List<Object> parsedInputs = exampleService.parseInExample(example.getInExample());

        String result;
        try {
            result = compileService.sendCodeToCompileServer(
                    request.getLanguage(),
                    request.getCode(),
                    parsedInputs,
                    example.getOutExample()
            );
        } catch (IOException | InterruptedException e) {
            log.error("컴파일 중 오류 발생: {}", e.getMessage(), e);
            return ErrorResponseDto.of(ErrorMessage.EXECUTION_FAILED);
        }

        log.info("컴파일 성공: {}", result);
        return ResponseDto.toResponseEntity(ResponseMessage.COMPILE_SUCCESS, result);
    }
}