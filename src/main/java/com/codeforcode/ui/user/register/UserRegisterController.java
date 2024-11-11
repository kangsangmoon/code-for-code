package com.codeforcode.ui.user.register;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.redis.token.TokenService;
import com.codeforcode.user.dto.UserRegisterRequest;
import com.codeforcode.user.dto.UserResponse;
import com.codeforcode.user.service.UserAuthService;
import com.codeforcode.util.HeaderUtil;
import io.jsonwebtoken.Header;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserAuthService userAuthService;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @Trace
    @PostMapping("/signup")
    public void signup(UserRegisterRequest request, HttpServletResponse response) {
        UserResponse signup = userAuthService.signup(request);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signup.getUserId(), signup.getPassword());

        String accessToken = tokenProvider.createAccessToken(authenticationToken);
        String refreshToken = tokenProvider.createRefreshToken(authenticationToken);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.addHeader(HeaderUtil.header, accessToken);
        response.addHeader(HeaderUtil.refreshHeader, refreshToken);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        String token = HeaderUtil.resolveRefreshToken(httpServletRequest);
        tokenService.deleteRefreshToken(token);

        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
