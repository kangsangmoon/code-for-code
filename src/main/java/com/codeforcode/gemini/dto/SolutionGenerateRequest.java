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
        return difficulty+" "+topic+" 문제를 출제해줘";
    }
}