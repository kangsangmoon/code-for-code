package com.codeforcode.user.controller;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.user.dto.UserRegisterRequest;
import com.codeforcode.user.repository.RankingService;
import com.codeforcode.user.repository.UserRepository;
import com.codeforcode.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserAuthService userAuthService;
    private final UserRepository userRepository;
    private final RankingService rankingService;


    @Trace
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @Trace
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userAuthService.signup(request));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userAuthService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userAuthService.getUserWithAuthorities(username));
    }

    @Trace
    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking() {
        return ResponseEntity.ok(rankingService.ranking());
    }
}