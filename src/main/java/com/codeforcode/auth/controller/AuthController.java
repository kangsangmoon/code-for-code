package com.codeforcode.auth.controller;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.dto.TokenDto;
import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.user.domain.User;
import com.codeforcode.user.domain.vo.Password;
import com.codeforcode.user.dto.LoginDto;
import com.codeforcode.user.dto.UserResponse;
import com.codeforcode.user.repository.UserRepository;
import com.codeforcode.user.service.UserAuthService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Trace
    @PostMapping
    public ResponseEntity<TokenDto> authorize(@RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());

        log.info(authenticationToken.toString());
        log.info(authenticationToken.getCredentials().toString());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); //여기
        log.info(authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("fin");

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(authorizationHeader, accessToken);
        httpHeaders.add(refreshHeader, refreshToken);

        return new ResponseEntity<>(new TokenDto(accessToken, refreshToken), httpHeaders, HttpStatus.OK);
    }

    @Trace
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        //TODO 패스워드 불일치로 로그인이 안 되는 상황을 해결해야함
        User loginResult = userRepository.login(loginDto.getUserId(), loginDto.getPassword());
        List<GrantedAuthority> grantedAuthorities = loginResult.grantedAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword(), grantedAuthorities);

        log.info(authenticationToken.toString());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("fin");


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
}