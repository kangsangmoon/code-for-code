package com.codeforcode.question.dto.response;

import com.codeforcode.question.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionPageDto {
    private String userName;
    private Long solutionId;
    private String title;
    private String context;
    private String code;
    private List<Comment> comments;
}
