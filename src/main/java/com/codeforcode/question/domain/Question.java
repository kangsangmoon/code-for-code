package com.codeforcode.question.domain;

import com.codeforcode.common.BaseEntity;
import com.codeforcode.question.dto.response.QuestionDto;
import com.codeforcode.solution.Solution;
import com.codeforcode.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    private Long solutionId;
    private String title;
    private String context;
    private String code;


    @Builder
    public Question(Long userId, Long solutionId, String title, String context, String code) {
        this.userId = userId;
        this.solutionId = solutionId;
        this.title = title;
        this.context = context;
        this.code = code;
    }

    public QuestionDto toDto() {
        return QuestionDto.builder()
                .solutionId(solutionId)
                .id(id)
                .userId(userId)
                .title(title)
                .context(context)
                .code(code)
                .build();
    }
}
