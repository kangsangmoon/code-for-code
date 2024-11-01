package com.codeforcode.question.dto.request;

import com.codeforcode.question.domain.Question;
import lombok.Data;

@Data
public class QuestionRegisterRequest {
    private Long userId;
    private Long solutionId;
    private String title;
    private String context;
    private String code;

    public Question toEntity() {
        return Question.builder()
                .userId(userId)
                .context(context)
                .code(code)
                .title(title)
                .build();
    }
}
