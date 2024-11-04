package com.codeforcode.user.dto;

import com.codeforcode.user.domain.User;
import com.codeforcode.user.domain.vo.Email;
import com.codeforcode.user.domain.vo.Name;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class UserRegisterRequest {
    private String userId;
    private String password;
    private String name;
    private String email;
    private String userName;

    @Builder
    public UserRegisterRequest(String userId, String password, String name, String email, String userName) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.userName = userName;
    }

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .password(password)
                .name(new Name(name))
                .email(new Email(email))
                .userName(userName)
                .build();
    }
}
