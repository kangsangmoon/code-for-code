package com.codeforcode.example.domain;

import com.codeforcode.common.BaseEntity;
import com.codeforcode.example.dto.response.ExampleResponse;
import com.codeforcode.restrictions.Domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Example extends BaseEntity implements Domain<ExampleResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inExample;

    private String outExample;

    private Long solutionId;

    public Example(String inExample, String outExample, Long solutionId) {
        this.inExample = inExample;
        this.outExample = outExample;
        this.solutionId = solutionId;
    }

    @Override
    public ExampleResponse toResponseDto() {
        return ExampleResponse.builder()
                .id(id)
                .inputExample(inExample)
                .outputExample(outExample)
                .build();
    }
}