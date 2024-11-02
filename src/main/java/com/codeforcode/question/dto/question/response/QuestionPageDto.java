package com.codeforcode.question.dto.question.response;

import com.codeforcode.question.domain.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionPageDto {
    private String userName;
    private String solutionTitle;
    private String title;
    private String context;
    private String code;
    private List<Comment> comments;

}
