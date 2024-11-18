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
@RequestMapping("/gemini")
public class GeminiUiController {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @RequestMapping
    public String geminiSolution(Model model, HttpServletRequest request) {
        String token = HeaderUtil.resolveToken(request);

        if (token != null && tokenProvider.validateToken(token)) {
            String name = tokenProvider.getAuthentication(token).getName();
            var userResponse = userRepository.findByUserId(name);

            model.addAttribute("user", userResponse);

            return "auth/gemini_solution";
        }

        return "redirect:/solutions/list";
    }
}