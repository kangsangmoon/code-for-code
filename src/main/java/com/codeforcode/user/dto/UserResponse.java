package com.codeforcode.user.dto;

import com.codeforcode.auth.domain.Authority;
import com.codeforcode.user.domain.vo.Email;
import com.codeforcode.user.domain.vo.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String userId;
    private String password;
    private Name name;
    private Email email;
    private String userName;
    private boolean activated;
    private Set<Authority> authorities;
    private Long point;
}
