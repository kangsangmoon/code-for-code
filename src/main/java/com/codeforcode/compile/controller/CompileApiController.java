package com.codeforcode.compile.controller;

import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.common.dto.ResponseDto;
import com.codeforcode.common.dto.ResponseMessage;
import com.codeforcode.compile.dto.CompileRequest;
import com.codeforcode.compile.service.CompileService;
import com.codeforcode.error.dto.ErrorMessage;
import com.codeforcode.error.dto.ErrorResponseDto;
import com.codeforcode.example.domain.Example;
import com.codeforcode.example.service.ExampleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/compile")
public class CompileApiController {

    private final CompileService compileService;
    private final ExampleRepository exampleRepository;
    private final TokenProvider tokenProvider;

    @PostMapping("/{solutionId}")
    public ResponseEntity<?> compileCode(
            @PathVariable Long solutionId,
            @RequestBody CompileRequest request,
            HttpServletRequest httpServletRequest
    ) {

        String token = tokenProvider.resolveToken(httpServletRequest);

        //TODO validateToken에는 유효하지 않을 시 예외를 발생시키는 로직이 이미 있습니다
        if (token == null || !tokenProvider.validateToken(token)) {
            log.warn("유효하지 않은 토큰입니다.");
            return null;
        }

        Example example = exampleRepository.getExampleBySolutionId(solutionId);
        if (example == null) {
            log.warn("문제에 맞는 예제를 찾지 못함: {}", solutionId);
            return ErrorResponseDto.of(ErrorMessage.EXAMPLE_NOT_FIND_BY_ID);
        }

        List<Object> parsedInputs = exampleRepository.parseInExample(example.getInExample());
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