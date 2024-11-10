package com.codeforcode.solution.dto.request;

import com.codeforcode.solution.domain.Solution;
import com.codeforcode.solution.domain.vo.Topic;
import lombok.Data;

@Data
public class SolutionRegisterRequest {
    private Long level;
    private String title;
    private String description;
    private Long solved;
    private String author;
    private String topic;

    public Solution toEntity(){
        return Solution.builder()
                .level(level)
                .title(title)
                .description(description)
                .solved(solved)
                .author(author)
                .topic(Topic.valueOf(topic))
                .build();
    }
}
