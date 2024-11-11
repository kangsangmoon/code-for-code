package com.codeforcode.ui.user;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.redis.token.TokenService;
import com.codeforcode.user.dto.UserRegisterRequest;
import com.codeforcode.user.dto.UserResponse;
import com.codeforcode.user.service.UserAuthService;
import com.codeforcode.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/register")
public class UserRegisterUiController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.refresh-header}")
    private String refreshHeader;

    @RequestMapping
    public String register() {
        return "non-auth/user/register";
    }
}
