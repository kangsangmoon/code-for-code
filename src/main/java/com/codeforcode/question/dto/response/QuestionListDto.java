package com.codeforcode.question.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionListDto {
    private Long id;
    private String title;
    private String userName;

    @QueryProjection
    public QuestionListDto(Long id, String title, String userName) {
        this.id = id;
        this.title = title;
        this.userName = userName;
    }
}
