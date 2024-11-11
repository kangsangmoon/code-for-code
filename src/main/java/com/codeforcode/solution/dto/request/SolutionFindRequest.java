package com.codeforcode.solution.dto.request;

import lombok.Data;

@Data
public class SolutionFindRequest {
    private Long level;
    private String title;
    private Long solved;
    private String author;
    private String topic;
}
