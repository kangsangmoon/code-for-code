package com.codeforcode.user.domain;

import com.codeforcode.auth.domain.Authority;
import com.codeforcode.user.domain.converter.PasswordEncodeConverter;
import com.codeforcode.user.domain.vo.Email;
import com.codeforcode.user.domain.vo.Name;
import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
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

   @Column(name = "nickname", length = 50)
   private String nickname;

   @Column(name = "activated")
   private boolean activated;

   @ManyToMany
   @JoinTable(
      name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
   private Set<Authority> authorities;
}