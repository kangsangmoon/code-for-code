package com.codeforcode.question.dto.question.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class QuestionJoinDto {
    private String userName;
    private String solutionTitle;
    private String title;
    private String context;
    private String code;

    @QueryProjection
    public QuestionJoinDto(String userName, String solutionTitle, String title, String context, String code) {
        this.userName = userName;
        this.solutionTitle = solutionTitle;
        this.title = title;
        this.context = context;
        this.code = code;
    }
}
