package com.codeforcode.redis.token;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/redis")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> save(TokenRegisterRequest request) {
        return ResponseEntity.ok(tokenService.save(request.getToken(), request.getUserId()));
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getUserByToken(@PathVariable(value = "token") String token) {
        return ResponseEntity.ok(tokenService.find(token));
    }
}