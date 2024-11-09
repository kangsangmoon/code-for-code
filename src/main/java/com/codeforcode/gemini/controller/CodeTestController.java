package com.codeforcode.gemini.controller;

import com.codeforcode.gemini.GeminiService;
import com.codeforcode.gemini.dto.SolutionGenerateRequest;
import com.codeforcode.util.CommonUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequestMapping("/ai-test")
@Controller
@RequiredArgsConstructor
public class CodeTestController {
    private final GeminiService geminiService;

    @GetMapping
    public void test(HttpServletResponse response) throws IOException {
        String markdown = CommonUtil.markdown(geminiService.getContents("코딩 테스트 사이트를 만드는 방법을 알려줘"));
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(markdown);
    }

    @GetMapping("/solution/test")
    public void testSolution(HttpServletResponse response) throws IOException {
        SolutionGenerateRequest solutionGenerateRequest = new SolutionGenerateRequest("탐욕 알고리즘","쉬운");
        log.info(solutionGenerateRequest.getMessage());
        String markdown = CommonUtil.markdown(geminiService.getContents(solutionGenerateRequest.getMessage()));
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        out.println("<h1> 주제 : " + solutionGenerateRequest.getTopic() + "</h1><br>");
        out.println("<h1> 난이도 : " + solutionGenerateRequest.getDifficulty() + "</h1><br>");
        out.println(markdown);
    }

    @GetMapping("/generate")
    public String generateSolution(Model model) throws IOException {
        SolutionGenerateRequest solutionGenerateRequest = new SolutionGenerateRequest();
        model.addAttribute("solutionGenerateRequest", solutionGenerateRequest);
        return "ai/generate";
    }

    @PostMapping("/generate")
    public void generateSolution(
            @RequestBody SolutionGenerateRequest solutionGenerateRequest,
            HttpServletResponse response
    ) throws IOException {
        String markdown = CommonUtil.markdown(geminiService.getContents(solutionGenerateRequest.getMessage()));
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<h1> 주제 : " + solutionGenerateRequest.getTopic() + "</h1><br>");
        out.println("<h1> 난이도 : " + solutionGenerateRequest.getDifficulty() + "</h1><br>");
        out.println(markdown);
    }
}