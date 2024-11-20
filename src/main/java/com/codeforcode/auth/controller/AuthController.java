package com.codeforcode.auth.controller;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.dto.TokenDto;
import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.error.excpetion.user.WrongPasswordException;
import com.codeforcode.user.domain.User;
import com.codeforcode.user.dto.LoginRequest;
import com.codeforcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.refresh-header}")
    private String refreshHeader;

    @Trace
    @PostMapping
    public ResponseEntity<TokenDto> authorize(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());

        log.info(authenticationToken.toString());
        log.info(authenticationToken.getCredentials().toString());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); //여기
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(authorizationHeader, accessToken);
        httpHeaders.add(refreshHeader, refreshToken);

        return new ResponseEntity<>(new TokenDto(accessToken, refreshToken), httpHeaders, HttpStatus.OK);
    }

    @Trace
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        //TODO 패스워드 불일치로 로그인이 안 되는 상황을 해결해야함
        User loginResult = userRepository.login(loginRequest);

        List<GrantedAuthority> grantedAuthorities = loginResult.grantedAuthorities();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword(), grantedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String accessToken = tokenProvider.createAccessToken(authenticationToken);
        String refreshToken = tokenProvider.createRefreshToken(authenticationToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(authorizationHeader, accessToken);
        httpHeaders.add(refreshHeader, refreshToken);

        return new ResponseEntity<>(new TokenDto(accessToken, refreshToken), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getAuth(@PathVariable(value = "token") String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        return ResponseEntity.ok(authentication);
    }

    @PostMapping("/login/exception/wrongpassword")
    public ResponseEntity<?> wrongPassword() {
        throw new WrongPasswordException();
    }
}