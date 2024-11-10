package com.codeforcode.solution.controller;

import com.codeforcode.solution.dto.request.SolutionFindRequest;
import com.codeforcode.solution.dto.request.SolutionRegisterRequest;
import com.codeforcode.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/solution")
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionRepository solutionRepository;

    @PostMapping
    public ResponseEntity<?> save(SolutionRegisterRequest request){
        return ResponseEntity.ok(solutionRepository.save(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(solutionRepository.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByCondition(SolutionFindRequest request){
        return ResponseEntity.ok(solutionRepository.findAll(request));
    }
}
