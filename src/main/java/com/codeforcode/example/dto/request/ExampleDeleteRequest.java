package com.codeforcode.example.dto.request;

import lombok.Data;

@Data
public class ExampleDeleteRequest {
    private Long solutionId;
    private Long exampleId;
}