package com.codeforcode.user.service;

import java.util.Collections;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.domain.Authority;
import com.codeforcode.error.excpetion.user.DuplicateUserException;
import com.codeforcode.error.excpetion.user.NotFoundMemberException;
import com.codeforcode.user.domain.User;
import com.codeforcode.user.domain.vo.Email;
import com.codeforcode.user.domain.vo.Name;
import com.codeforcode.user.dto.UserDto;
import com.codeforcode.user.dto.UserRegisterRequest;
import com.codeforcode.user.dto.UserResponse;
import com.codeforcode.user.repository.UserAuthRepository;
import com.codeforcode.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserAuthRepository userAuthRepository;

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userAuthRepository.findOneWithAuthoritiesByUserId(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userAuthRepository::findOneWithAuthoritiesByUserId)
                        .orElseThrow(NotFoundMemberException::new)
        );
    }
}