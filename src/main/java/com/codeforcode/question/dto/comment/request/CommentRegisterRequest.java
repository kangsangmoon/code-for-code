package com.codeforcode.question.dto.comment.request;

import com.codeforcode.question.domain.Comment;
import lombok.Data;

@Data
public class CommentRegisterRequest {
    private Long questionId;
    private Long userId;
    private String context;

    public Comment toEntity(){
        return Comment.builder()
                .userId(userId)
                .questionId(questionId)
                .context(context)
                .build();
    }
}
