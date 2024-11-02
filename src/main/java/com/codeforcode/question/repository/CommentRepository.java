package com.codeforcode.question.repository;

import com.codeforcode.question.domain.Comment;
import com.codeforcode.question.dto.comment.request.CommentRegisterRequest;
import com.codeforcode.question.dto.comment.response.CommentDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeforcode.question.domain.QComment.comment;

@Repository
public class CommentRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CommentRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public CommentDto save(CommentRegisterRequest request) {
        Comment entity = request.toEntity();
        em.persist(entity);
        return entity.toDto();
    }

    @Transactional(readOnly = true)
    public CommentDto findById(Long id) {
        Comment result = queryFactory
                .selectFrom(comment)
                .fetchOne();

        return result != null ? result.toDto() : null;
    }

    @Transactional(readOnly = true)
    public List<CommentDto> findAll() {
        return queryFactory
                .selectFrom(comment)
                .fetch()
                .stream()
                .map(Comment::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentDto> findAllByQuestionId(Long questionId) {
        return queryFactory
                .selectFrom(comment)
                .where(questionIdEq(questionId))
                .fetch().stream()
                .map(Comment::toDto)
                .toList();
    }

    private BooleanExpression questionIdEq(Long questionId) {
        return questionId != null ? comment.questionId.eq(questionId) : null;
    }
}