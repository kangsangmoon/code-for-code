package com.codeforcode.ui.user;

import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.user.repository.UserRepository;
import com.codeforcode.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/info")
public class UserInfoUiController {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @GetMapping
    public String userInfo(Model model, HttpServletRequest request) {
        String token = HeaderUtil.resolveToken(request);
        if (tokenProvider.validateToken(token)) {
            String userId = tokenProvider.getAuthentication(token).getName();
            var userResponse = userRepository.findByUserId(userId);
            model.addAttribute("user", userResponse);
        }
        return "user/info";
    }
}