package com.codeforcode.gemini.controller;

import com.codeforcode.gemini.GeminiService;
import com.codeforcode.gemini.dto.FreeChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping("/test")
    public ResponseEntity<?> gemini() {
        try {
            return ResponseEntity.ok().body(geminiService.getContents("안녕! 너는 누구야?"));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/free-chat")
    public ResponseEntity<?> free(@RequestBody FreeChatRequest request){
        try {
            return ResponseEntity.ok().body(geminiService.getContents(request.getMessage()));
        }catch (HttpClientErrorException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/chat")
    public ResponseEntity<?> getChat(@RequestBody FreeChatRequest request){
        try {
            return ResponseEntity.ok().body(geminiService.getChat(request.getMessage()));
        }catch (HttpClientErrorException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}