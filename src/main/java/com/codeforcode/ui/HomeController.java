package com.codeforcode.ui;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.redis.token.TokenService;
import com.codeforcode.user.dto.UserResponse;
import com.codeforcode.user.repository.UserRepository;
import com.codeforcode.user.service.UserAuthService;
import com.codeforcode.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Trace
    @RequestMapping
    public String home(Model model,
                       HttpServletRequest req,
                       HttpServletResponse res)
    {
        String token = HeaderUtil.resolveRefreshToken(req);
        if(tokenProvider.validateToken(token)) {
            String name = tokenProvider.getAuthentication(token).getName();
            var userResponse = userRepository.findByUserId(name);
            model.addAttribute("user", userResponse);
            return "auth/home";
        }
        return "non-auth/home";
    }
}
