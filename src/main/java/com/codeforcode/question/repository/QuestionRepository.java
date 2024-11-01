package com.codeforcode.question.repository;

import com.codeforcode.question.domain.Comment;
import com.codeforcode.question.domain.Question;
import com.codeforcode.question.dto.request.QuestionRegisterRequest;
import com.codeforcode.question.dto.response.QQuestionListDto;
import com.codeforcode.question.dto.response.QuestionDto;
import com.codeforcode.question.dto.response.QuestionListDto;
import com.codeforcode.question.dto.response.QuestionPageDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeforcode.question.domain.QQuestion.question;
import static com.codeforcode.question.domain.QComment.comment;
import static com.codeforcode.user.domain.QUser.user;

@Repository
public class QuestionRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public QuestionRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public QuestionDto save(QuestionRegisterRequest request) {
        Question entity = request.toEntity();
        em.persist(entity);
        return entity.toDto();
    }

    @Transactional(readOnly = true)
    public QuestionDto findById(Long id) {
        Question result = queryFactory
                .selectFrom(question)
                .where(questionIdEq(id))
                .fetchOne();

        return result != null ? result.toDto() : null;
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findAll() {
        return queryFactory
                .selectFrom(question)
                .fetch()
                .stream()
                .map(Question::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionListDto> findBySolutionId(Long solutionId) {
        return queryFactory
                .select(new QQuestionListDto(
                        question.id.as("id"),
                        question.title.as("title"),
                        user.nickname.as("userName")
                ))
                .from(question)
                .where(solutionIdEq(solutionId))
                .leftJoin(question).on(user.id.eq(question.userId)).fetch();
    }

    @Transactional(readOnly = true)
    public QuestionPageDto findQuestionByQuestionId(Long questionId) {
        List<Comment> commentResult = queryFactory
                .selectFrom(comment)
                .where(questionIdEq(questionId))
                .fetch();

        Question questionResult = queryFactory
                .selectFrom(question)
                .where(questionIdEq(questionId))
                .fetchOne();

        String userName = queryFactory
                .select(user.nickname)
                .from(user)
                .where(userIdEq(questionResult.getUserId()))
                .fetchOne();

        return new QuestionPageDto(
                userName,
                null,
                questionResult.getTitle(),
                questionResult.getContext(),
                questionResult.getCode(),
                commentResult
        );

    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? user.id.eq(userId) : null;
    }

    private BooleanExpression questionIdEq(Long questionId) {
        return questionId != null ? question.id.eq(questionId) : null;
    }

    private BooleanExpression solutionIdEq(Long solutionId) {
        return solutionId != null ? question.solutionId.eq(solutionId) : null;
    }
}