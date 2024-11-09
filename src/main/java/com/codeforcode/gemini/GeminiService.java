package com.codeforcode.gemini;

import com.codeforcode.gemini.dto.ChatRequest;
import com.codeforcode.gemini.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Qualifier("geminiRestTemplate")
    private RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String getContents(String prompt) {
        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        ChatRequest request = new ChatRequest(prompt);
        ChatResponse response = restTemplate.postForObject(requestUrl, request, ChatResponse.class);

        String message = response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText()
                .toString();

        return message;
    }

    public ChatResponse getChat(String prompt) {
        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        ChatRequest request = new ChatRequest(prompt);

        return restTemplate.postForObject(requestUrl, request, ChatResponse.class);
    }
}