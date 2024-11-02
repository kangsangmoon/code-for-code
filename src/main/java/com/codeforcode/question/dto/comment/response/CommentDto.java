package com.codeforcode.question.dto.comment.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long questionId;
    private Long userId;
    private String context;

    @Builder
    public CommentDto(Long id, Long questionId, Long userId, String context) {
        this.id = id;
        this.questionId = questionId;
        this.userId = userId;
        this.context = context;
    }
}
