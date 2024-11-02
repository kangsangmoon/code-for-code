package com.codeforcode;

import com.codeforcode.auth.domain.Authority;
import com.codeforcode.question.domain.Comment;
import com.codeforcode.question.domain.Question;
import com.codeforcode.question.dto.response.QQuestionListDto;
import com.codeforcode.question.dto.question.response.QuestionDto;
import com.codeforcode.question.dto.question.response.QuestionListDto;
import com.codeforcode.user.domain.User;
import com.codeforcode.user.domain.vo.Email;
import com.codeforcode.user.domain.vo.Name;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.codeforcode.question.domain.QQuestion.question;
import static com.codeforcode.user.domain.QUser.user;

@SpringBootTest
@Transactional
public class QuestionTest {

    @PersistenceContext
    private EntityManager em;
    private JPAQueryFactory queryFactory;


    @BeforeEach
    public void setUp() {
        queryFactory = new JPAQueryFactory(em);

        Question question1 = new Question(
                1L,
                1L,
                "title1",
                "context1",
                "code1"
        );

        Question question2 = new Question(
                2L,
                2L,
                "title2",
                "context2",
                "code2"
        );

        Question question3 = new Question(
                3L,
                3L,
                "title3",
                "context3",
                "code3"
        );

        em.persist(question1);
        em.persist(question2);
        em.persist(question3);

        Comment comment1 = new Comment(
                1L,
                1L,
                "comment1"
        );

        Comment comment2 = new Comment(
                2L,
                2L,
                "comment2"
        );

        Comment comment3 = new Comment(
                3L,
                3L,
                "comment3"
        );

        em.persist(comment1);
        em.persist(comment2);
        em.persist(comment3);

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user1 = new User(
                "userId",
                "qpwoei123!",
                new Name("김기현"),
                new Email("rlarlgus0206@naver.com"),
                "kim",
                true,
                Collections.singleton(authority)
        );

        User user2 = new User(
                "userId2",
                "qpwoei123",
                new Name("이하연"),
                new Email("kim@naver.com"),
                "kimkihyun",
                true,
                Collections.singleton(authority)
        );

        em.persist(user1);
        //em.persist(user2);
    }

    @Test
    public void test1() {
        List<QuestionListDto> fetch = queryFactory
                .select(new QQuestionListDto(
                        question.id.as("id"),
                        question.title.as("title"),
                        user.userName.as("userName")
                ))
                .from(question)
                .where(question.solutionId.eq(1L))
                .join(user)
                .on(user.id.eq(question.userId))
                .fetch();

        fetch.forEach(System.out::println);
    }

    @Test
    public void getAllQuestion(){
        List<QuestionListDto> fetch = queryFactory
                .select(new QQuestionListDto(
                        question.id.as("id"),
                        question.title.as("title"),
                        user.userName.as("userName")
                ))
                .from(question)
                .join(user)
                .on(user.id.eq(question.userId))
                .fetch();

        fetch.forEach(System.out::println);
    }

    @Test
    public void test2(){
        List<QuestionDto> list = queryFactory
                .selectFrom(question)
                .fetch()
                .stream()
                .map(Question::toDto)
                .toList();

        for (QuestionDto dto : list) {
            System.out.println(dto.getId());
            System.out.println(dto.getTitle());
        }
    }
}
