package com.codeforcode.example.dto.request;

import com.codeforcode.example.domain.Example;
import com.codeforcode.restrictions.RegisterRequest;
import lombok.Data;

@Data
public class ExampleRegisterRequest implements RegisterRequest<Example> {
    private String inExample;
    private String outExample;
    private Long solutionId;

    @Override
    public Example toEntity() {
        return new Example(
                this.inExample,
                this.outExample,
                this.solutionId
        );
    }
}