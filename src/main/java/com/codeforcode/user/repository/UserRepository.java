package com.codeforcode.user.repository;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.domain.Authority;
import com.codeforcode.error.excpetion.user.DuplicateUserException;
import com.codeforcode.error.excpetion.user.WrongIdException;
import com.codeforcode.error.excpetion.user.WrongPasswordException;
import com.codeforcode.user.domain.User;
import com.codeforcode.user.domain.vo.Email;
import com.codeforcode.user.domain.vo.Name;
import com.codeforcode.user.dto.LoginRequest;
import com.codeforcode.user.dto.UserRegisterRequest;
import com.codeforcode.user.dto.UserResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    public User login(LoginRequest loginRequest) {
        User result = queryFactory.selectFrom(user)
                .where(
                        user.userId.eq(loginRequest.getUserId())
                )
                .fetchOne();

        if (result == null) throw new WrongIdException();
        else if(passwordEncoder.matches(loginRequest.getPassword(), result.getPassword())) return result;
        else throw new WrongPasswordException();
    }

    @Trace
    @Transactional
    public UserResponse signup(@Valid UserRegisterRequest request) {
        long count = queryFactory.selectFrom(user)
                .where(userIdEq(request.getUserId()))
                .stream().count();

        if(count > 0) throw new DuplicateUserException();



        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .userId(request.getUserId())
                .name(new Name(request.getName()))
                .email(new Email(request.getEmail()))
                .password(request.getPassword())
                .userName(request.getUserName())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .point(0L)
                .build();

        em.persist(user);

        return user.toResponse();
    }

    private BooleanExpression userIdEq(String userId) {
        return userId != null ? user.userId.eq(userId) : null;
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
    public List<UserResponse> findAll(){
        return queryFactory
                .selectFrom(user)
                .fetch()
                .stream()
                .map(User::toResponse)
                .toList();
    }
}
