package com.codeforcode.auth.service;

import com.codeforcode.auth.dto.TokenDto;
import com.codeforcode.auth.jwt.TokenProvider;
import com.codeforcode.error.excpetion.user.LoginFailException;
import com.codeforcode.user.domain.User;
import com.codeforcode.user.dto.LoginDto;
import com.codeforcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String userId) {
      return userRepository.findOneWithAuthoritiesByUserId(userId)
         .map(user -> createUser(userId, user))
         .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다."));
   }

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