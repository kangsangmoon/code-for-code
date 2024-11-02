package com.codeforcode.question.dto.question.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private Long userId;
    private Long solutionId;
    private String title;
    private String context;
    private String code;

    @Builder
    public QuestionDto(Long id, Long userId, Long solutionId, String title, String context, String code) {
        this.id = id;
        this.userId = userId;
        this.solutionId = solutionId;
        this.title = title;
        this.context = context;
        this.code = code;
    }
}
