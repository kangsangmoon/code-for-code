package com.codeforcode.ui.solution;

import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.redis.token.TokenService;
import com.codeforcode.solution.dto.response.SolutionResponse;
import com.codeforcode.solution.repository.SolutionRepository;
import com.codeforcode.user.dto.UserResponse;
import com.codeforcode.user.service.UserAuthService;
import com.codeforcode.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/solution")
public class SolutionUiController {

    private final SolutionRepository solutionRepository;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;
    private final UserAuthService userAuthService;

    @RequestMapping
    public String solutionList(Model model, HttpServletRequest request) {
        List<SolutionResponse> solutionList = solutionRepository.findAll();
        model.addAttribute("solutions", solutionList);

        String token = HeaderUtil.resolveToken(request);
        if(tokenProvider.validateToken(token)){
            Long userId = tokenService.find(token);
            UserResponse user = userAuthService.findById(userId);
            model.addAttribute("user", user);
            return "/auth/solution";
        }
        return "/non-auth/solution";
    }

    @RequestMapping("/{id}")
    public String solutionSolvePage(Model model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
        SolutionResponse result = solutionRepository.findById(id);
        model.addAttribute("solution", result);

        String token = HeaderUtil.resolveToken(request);

        if(tokenProvider.validateToken(token)){
            Long userId = tokenService.find(token);
            UserResponse user = userAuthService.findById(userId);
            model.addAttribute("user", user);
            return "/auth/solution";
        }
        return "/non-auth/solution";
    }
}
