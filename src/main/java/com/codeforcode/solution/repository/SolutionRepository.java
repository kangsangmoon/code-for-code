package com.codeforcode.solution.repository;

import com.codeforcode.solution.domain.Solution;
import com.codeforcode.solution.dto.request.SolutionFindRequest;
import com.codeforcode.solution.dto.request.SolutionRegisterRequest;
import com.codeforcode.solution.dto.response.SolutionResponse;
import com.codeforcode.solution.domain.vo.Topic;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeforcode.solution.domain.QSolution.solution;

@Repository
public class SolutionRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public SolutionRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public SolutionResponse save(SolutionRegisterRequest request) {
        Solution solution = request.toEntity();
        em.persist(solution);
        return solution.toResponse();
    }

    @Transactional(readOnly = true)
    public SolutionResponse findById(Long id) {
        Solution result = queryFactory.selectFrom(solution)
                .where(idEq(id))
                .fetchOne();

        return result != null ? result.toResponse() : null;
    }

    @Transactional(readOnly = true)
    public List<SolutionResponse> findAll() {
        return queryFactory
                .selectFrom(solution)
                .fetch()
                .stream()
                .map(Solution::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SolutionResponse> findAll(SolutionFindRequest request) {
        return queryFactory
                .selectFrom(solution)
                .where(
                        levelEq(request.getLevel()),
                        authorEq(request.getAuthor()),
                        titleContains(request.getTitle()),
                        topicEq(request.getTopic())
                )
                .fetch()
                .stream()
                .map(Solution::toResponse)
                .toList();
    }

    private BooleanExpression idEq(Long id) {
        return id != null ? solution.id.eq(id) : null;
    }

    private BooleanExpression levelEq(Long level) {
        return level != null ? solution.level.eq(level) : null;
    }

    private BooleanExpression authorEq(String author) {
        return author != null ? solution.author.eq(author) : null;
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? solution.title.contains(title) : null;
    }

    private BooleanExpression topicEq(String topic) {
        return topic != null ? solution.topic.eq(Topic.valueOf(topic)) : null;
    }
}
