package com.codeforcode.user.domain;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.auth.domain.Authority;
import com.codeforcode.common.BaseEntity;
import com.codeforcode.question.domain.Question;
import com.codeforcode.user.domain.converter.PasswordEncodeConverter;
import com.codeforcode.user.domain.vo.Email;
import com.codeforcode.user.domain.vo.Name;
import com.codeforcode.user.dto.UserDto;
import com.codeforcode.user.dto.UserResponse;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "user_id")
   private String userId;

   @Column
   @Convert(converter = PasswordEncodeConverter.class)
   private String password;

   @Embedded
   @Column
   private Name name;

   @Column
   @Embedded
   private Email email;

   @Column(name = "user_name")
   private String userName;

   @Column(name = "activated")
   private boolean activated;

   private Long point;

   @ManyToMany
   @JoinTable(
      name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
   private Set<Authority> authorities;

   @Builder
   public User(String userId, String password, Name name, Email email, String userName, boolean activated, Set<Authority> authorities, Long point) {
      this.userId = userId;
      this.password = password;
      this.name = name;
      this.email = email;
      this.userName = userName;
      this.activated = activated;
      this.authorities = authorities;
      this.point = point;
   }

   public UserResponse toResponse() {
      return UserResponse.builder()
              .id(id).userId(userId)
              .password(password)
              .name(name)
              .email(email)
              .userName(userName)
              .activated(activated)
              .point(point)
              .authorities(authorities).build();
   }

   @Trace
   public List<GrantedAuthority> grantedAuthorities() {
      if (!this.isActivated()) {
         throw new RuntimeException(userId + " -> 활성화되어 있지 않습니다.");
      }

      return this.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
              .collect(Collectors.toList());

   }
}