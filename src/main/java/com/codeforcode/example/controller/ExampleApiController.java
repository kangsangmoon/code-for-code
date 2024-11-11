package com.codeforcode.example.controller;


import com.codeforcode.common.dto.ResponseDto;
import com.codeforcode.common.dto.ResponseMessage;
import com.codeforcode.example.dto.request.ExampleDeleteRequest;
import com.codeforcode.example.dto.request.ExampleRegisterRequest;
import com.codeforcode.example.service.ExampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/example")
@RequiredArgsConstructor
public class ExampleApiController {
    private final ExampleRepository exampleRepository;

    @PostMapping("/examples")
    public ResponseEntity<?> addExampleToSolution(@RequestBody ExampleRegisterRequest request) {
        var response = exampleRepository.exampleRegister(request);
        return ResponseDto.toResponseEntity(ResponseMessage.EXAMPLE_CREATION_SUCCESSFUL, response);
    }

    @DeleteMapping("/examples")
    public ResponseEntity<?> removeExampleFromSolution(@RequestBody ExampleDeleteRequest request) {
        exampleRepository.exampleDelete(request);

        return ResponseEntity.noContent().build();
    }
}