package com.codeforcode.question.controller;

import com.codeforcode.question.dto.request.QuestionRegisterRequest;
import com.codeforcode.question.repository.QuestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
public class QuestionController {
    private final QuestionRepository questionRepository;

    @Operation
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(questionRepository.findById(id));
    }

    @Operation
    @GetMapping("/all")
    public ResponseEntity<?> getAllQuestions() {
        return ResponseEntity.ok(questionRepository.findAll());
    }

    @Operation
    @PostMapping
    public ResponseEntity<?> registerQuestion(QuestionRegisterRequest request) {
        return ResponseEntity.ok(questionRepository.save(request));
    }

    @Operation
    @GetMapping("/solution/{solutionId}")
    public ResponseEntity<?> getSolutionById(@PathVariable(value = "solutionId") Long solutionId) {
        return ResponseEntity.ok(questionRepository.findBySolutionId(solutionId));
    }
}
