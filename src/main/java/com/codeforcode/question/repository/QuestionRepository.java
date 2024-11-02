package com.codeforcode.question.repository;

import com.codeforcode.question.domain.Comment;
import com.codeforcode.question.domain.Question;
import com.codeforcode.question.dto.question.request.QuestionRegisterRequest;
import com.codeforcode.question.dto.question.response.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeforcode.question.domain.QQuestion.question;
import static com.codeforcode.question.domain.QComment.comment;
import static com.codeforcode.solution.QSolution.solution;
import static com.codeforcode.user.domain.QUser.user;

@Repository
public class QuestionRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public QuestionRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * @implNote Question을 저장하는 메소드
     */
    @Transactional
    public QuestionDto save(QuestionRegisterRequest request) {
        Question entity = request.toEntity();
        em.persist(entity);
        return entity.toDto();
    }

    /**
     * @param id Question domain의 PK
     * @implNote Question을 PK로 찾는 메소드
     */
    @Transactional(readOnly = true)
    public QuestionDto findById(Long id) {
        Question result = queryFactory
                .selectFrom(question)
                .where(questionIdEq(id))
                .fetchOne();

        return result != null ? result.toDto() : null;
    }

    /**
     * @implNote 모든 Question을 찾는 메소드
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> findAll() {
        return queryFactory
                .selectFrom(question)
                .fetch()
                .stream()
                .map(Question::toDto)
                .toList();
    }

    /**
     * @param solutionId Question의 FK
     * @implNote solutionId(KF)로 Question을 찾는 메소드
     * @apiNote QuestionListDto는 페이지를 로딩하는데 필요한 정보를 담는 객체이다
     */
    @Transactional(readOnly = true)
    public List<QuestionListDto> findBySolutionId(Long solutionId) {
        return queryFactory
                .select(new QQuestionListDto(
                        question.id.as("id"),
                        question.title.as("title"),
                        user.userName.as("userName")
                ))
                .from(question)
                .where(solutionIdEq(solutionId))
                .join(user)
                .on(user.id.eq(question.userId))
                .fetch();
    }

    /**
     * @param questionId 게시글 조회를 위해서 Question의 PK가 필요하다
     * @implNote 질문 게시판에서 게시글을 선택해서 들어갔을 때 보여주는 정보를 가져오기 위한 메소드
     * @apiNote 게시글의 정보, 게시글에 달린 댓글, 게시글을 작성자에 대한 정보를 가져온다
     */
    @Transactional(readOnly = true)
    public QuestionPageDto findQuestionByQuestionId(Long questionId) {
        List<Comment> commentResult = queryFactory
                .selectFrom(comment)
                .where(questionIdEq(questionId))
                .fetch();

        QuestionJoinDto joinDto = queryFactory.select(
                        new QQuestionJoinDto(
                                user.userName,
                                solution.title.as("solutionTitle"),
                                question.title,
                                question.context,
                                question.code
                        )
                ).from(question)
                .join(solution)
                .on(question.solutionId.eq(solution.id))
                .join(user)
                .on(user.id.eq(question.userId))
                .where(questionIdEq(questionId))
                .fetchOne();

        return new QuestionPageDto(
                joinDto.getUserName(),
                joinDto.getSolutionTitle(),
                joinDto.getTitle(),
                joinDto.getContext(),
                joinDto.getCode(),
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