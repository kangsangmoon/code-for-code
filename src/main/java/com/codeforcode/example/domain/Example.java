package com.codeforcode.example.domain;

import com.codeforcode.common.BaseEntity;
import com.codeforcode.example.dto.response.ExampleResponse;
import com.codeforcode.restrictions.Domain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`example`")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Example extends BaseEntity implements Domain<ExampleResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inExample;

    private String outExample;

    @Column(name = "solution_id")
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