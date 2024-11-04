package com.codeforcode.user.service;

import java.util.Collections;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.domain.Authority;
import com.codeforcode.error.excpetion.user.DuplicateUserException;
import com.codeforcode.error.excpetion.user.NotFoundMemberException;
import com.codeforcode.user.domain.User;
import com.codeforcode.user.domain.vo.Email;
import com.codeforcode.user.domain.vo.Name;
import com.codeforcode.user.domain.vo.Password;
import com.codeforcode.user.dto.UserDto;
import com.codeforcode.user.dto.UserRegisterRequest;
import com.codeforcode.user.dto.UserResponse;
import com.codeforcode.user.repository.UserRepository;
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
public class UserService {
    private final UserRepository userRepository;

    /*public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }*/

    @Trace
    @Transactional
    public UserResponse signup(@Valid UserRegisterRequest request) {
        if (userRepository.findOneWithAuthoritiesByUserId(request.getUserId()).orElse(null) != null) {
            throw new DuplicateUserException();
        }

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
                .build();

        User save = userRepository.save(user);

        return save.toResponse();
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUserId(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUserId)
                        .orElseThrow(NotFoundMemberException::new)
        );
    }
}