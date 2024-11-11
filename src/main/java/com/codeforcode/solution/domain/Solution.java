package com.codeforcode.solution.domain;

import com.codeforcode.common.BaseEntity;
import com.codeforcode.solution.dto.response.SolutionResponse;
import com.codeforcode.solution.domain.vo.Topic;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`solution`")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Solution extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long level;
    private String title;
    private String description;
    private Long solved;
    private String author;
    private Topic topic;

    @Builder
    public Solution(Long level, String title, String description, Long solved, String author, Topic topic) {
        this.level = level;
        this.title = title;
        this.description = description;
        this.solved = solved;
        this.author = author;
        this.topic = topic;
    }

    public SolutionResponse toResponse() {
        return SolutionResponse.builder()
                .id(this.id)
                .level(this.level)
                .title(this.title)
                .author(this.author)
                .solved(this.solved)
                .description(this.description)
                .topic(this.topic)
                .build();
    }
}
