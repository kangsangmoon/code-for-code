package com.codeforcode.user.repository;

import com.codeforcode.user.domain.User;
import com.codeforcode.user.dto.UserResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeforcode.user.domain.QUser.user;

@Slf4j
@Repository
public class UserRepository {

    private final PasswordEncoder passwordEncoder;
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public UserRepository(EntityManager em, PasswordEncoder passwordEncoder) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public User login(String userId, String password) {
        User result = queryFactory.selectFrom(user)
                .where(
                        user.userId.eq(userId)
                )
                .fetchOne();
        log.info(result.getUserId());

        if(result != null && passwordEncoder.matches(password, result.getPassword())) {
            return result;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public User login(String userId) {

        return queryFactory.selectFrom(user)
                .where(user.userId.eq(userId))
                .fetchOne();
    }

    @Transactional(readOnly = true)
    public UserResponse findByUserId(String userId) {
        User result = queryFactory
                .selectFrom(user)
                .where(user.userId.eq(userId))
                .fetchOne();

        return result != null ? result.toResponse() : null;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> ranking() {
        List<User> fetch = queryFactory.selectFrom(user)
                .orderBy(user.point.desc()).fetch();

        return fetch != null ? fetch.stream().map(User::toResponse).toList() : null;
    }
}
