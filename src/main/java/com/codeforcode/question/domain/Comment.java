package com.codeforcode.question.domain;

import com.codeforcode.common.BaseEntity;
import com.codeforcode.question.dto.comment.response.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long questionId;
    private Long userId;
    private String context;

    @Builder
    public Comment(Long questionId, Long userId, String context) {
        this.questionId = questionId;
        this.userId = userId;
        this.context = context;
    }

    public CommentDto toDto(){
        return CommentDto
                .builder()
                .id(id)
                .questionId(questionId)
                .userId(userId)
                .context(context)
                .build();
    }
}
