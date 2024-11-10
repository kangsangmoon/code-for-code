package com.codeforcode.solution.dto.response;

import com.codeforcode.solution.domain.vo.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SolutionResponse {
    private Long id;
    private Long level;
    private String title;
    private String description;
    private Long solved;
    private String author;
    private Topic topic;
}
