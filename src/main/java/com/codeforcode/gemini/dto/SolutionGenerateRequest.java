package com.codeforcode.gemini.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolutionGenerateRequest {
    private String topic;
    private String difficulty;

    public SolutionGenerateRequest(String topic, String difficulty) {
        this.topic = topic;
        this.difficulty = difficulty;
    }

    public String getMessage() {
        return difficulty+" "+topic+" 문제를 출제해줘. 답은 보여주지 말고. 문제, 입력 예제, 출력 예제를 알려줘. 다른 사이트는 추천하지 말아줘.";
    }
}