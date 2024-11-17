package com.codeforcode.ui.gemini;

import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.user.repository.UserRepository;
import com.codeforcode.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ai_solution")
public class GeminiUiController {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @RequestMapping
    public String aiProblem(Model model, HttpServletRequest request) {

        String token = HeaderUtil.resolveToken(request);
        if(tokenProvider.validateToken(token)){
            String name = tokenProvider.getAuthentication(token).getName();
            var userResponse = userRepository.findByUserId(name);
            model.addAttribute("user", userResponse);
            return "/ai/ai-solution";
        }
        return "/";
    }
}
