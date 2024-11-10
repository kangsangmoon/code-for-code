package com.codeforcode.example.dto.response;

import com.codeforcode.example.domain.Example;
import com.codeforcode.restrictions.ResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
public class ExampleResponse implements ResponseDto<Example> {
    private Long id;
    private String inputExample;
    private String outputExample;
    private Long solutionId;

    @Builder
    public ExampleResponse(Long id, String inputExample, String outputExample) {
        this.id = id;
        this.inputExample = inputExample;
        this.outputExample = outputExample;
    }

    @Override
    public Example toEntity() {
        return new Example(
                this.inputExample,
                this.outputExample,
                this.solutionId
        );
    }
}