package com.codeforcode.example.service;

import com.codeforcode.error.excpetion.example.ExampleNotFindByIdException;
import com.codeforcode.example.domain.Example;
import com.codeforcode.example.domain.ExampleParser;
import com.codeforcode.example.dto.request.ExampleDeleteRequest;
import com.codeforcode.example.dto.request.ExampleRegisterRequest;
import com.codeforcode.example.dto.response.ExampleResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeforcode.example.domain.QExample.example;

@Service
public class ExampleRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    private final ExampleParser exampleParser;

    public ExampleRepository(EntityManager em,ExampleParser exampleParser) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.exampleParser = exampleParser;
    }

    @Transactional
    public ExampleResponse exampleRegister(ExampleRegisterRequest request) {
        Example entity = request.toEntity();
        em.persist(entity);
        return entity.toResponseDto();
    }

    @Transactional
    public void exampleDelete(ExampleDeleteRequest request) {
        queryFactory
                .delete(example)
                .where(idEq(request.getExampleId()))
                .execute();
    }

    private BooleanExpression idEq(Long id) {
        return id != null ? example.id.eq(id) : null;
    }

    public Example getExampleBySolutionId(Long solutionId) {
        Example result = queryFactory.selectFrom(example)
                .where(solutionIdEq(solutionId)).fetchOne();

        if (result == null) {
            throw new ExampleNotFindByIdException();
        }
        return result;
    }

    private BooleanExpression solutionIdEq(Long solutionId) {
        return solutionId != null ? example.id.eq(solutionId) : null;
    }

    public List<Object> parseInExample(String inExample) {
        return exampleParser.parseInExample(inExample);
    }
}