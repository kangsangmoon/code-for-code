package com.codeforcode.redis.token;

import com.codeforcode.error.excpetion.user.NotFoundMemberException;
import com.codeforcode.user.domain.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.codeforcode.user.domain.QUser.user;

@Repository
public class TokenRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public TokenRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * @apiNote id(pk)로 user를 조회할 수 있도록 함
     * */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        User result = queryFactory
                .selectFrom(user)
                .where(userIdEq(id))
                .fetchOne();

        if(result == null) throw new NotFoundMemberException();
        return result;
    }

    private BooleanExpression userIdEq(Long id) {
        return id != null ? user.id.eq(id) : null;
    }
}