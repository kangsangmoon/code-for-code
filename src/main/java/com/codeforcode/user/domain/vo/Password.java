package com.codeforcode.user.domain.vo;

import com.codeforcode.error.excpetion.user.InvalidPasswordException;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Password {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
    private String password;

    public Password(String password) {
        validatePassword(password);
    }

    private void validatePassword(String password) {
        if(!Pattern.matches(PASSWORD_REGEX, password)){
            throw new InvalidPasswordException();
        }
        this.password = password;
    }

    public void setEncodePassword(String encodePassword) {
        this.password = encodePassword;
    }
}