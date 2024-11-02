package com.codeforcode.question.controller.comment;

import com.codeforcode.question.dto.comment.request.CommentRegisterRequest;
import com.codeforcode.question.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<?> register(CommentRegisterRequest request){
        return ResponseEntity.ok(commentRepository.save(request));
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(commentRepository.findAll());
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> get(@PathVariable(value = "commentId") Long commentId){
        return ResponseEntity.ok(commentRepository.findById(commentId));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<?> findBuQuestionId(@PathVariable(value = "questionId") Long questionId){
        return ResponseEntity.ok(commentRepository.findAllByQuestionId(questionId));
    }
}
