package com.codeforcode.auth.service;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.user.domain.User;
import com.codeforcode.user.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
   private final UserAuthRepository userAuthRepository;

   @Trace
   @Override
   @Transactional
   public UserDetails loadUserByUsername(String userId) {
      org.springframework.security.core.userdetails.User result = userAuthRepository.findOneWithAuthoritiesByUserId(userId)
              .map(user -> createUser(userId, user))
              .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다."));

      log.info(result.toString());
      log.info(result.getPassword());

      return result;
   }

   @Trace
   private org.springframework.security.core.userdetails.User createUser(String userId, User user) {
      if (!user.isActivated()) {
         throw new RuntimeException(userId + " -> 활성화되어 있지 않습니다.");
      }

      List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
              .collect(Collectors.toList());

      return new org.springframework.security.core.userdetails.User(
              user.getUserId(),
              user.getPassword(),
              grantedAuthorities
      );
   }
}