package com.codeforcode.user.domain.vo;

import com.codeforcode.error.excpetion.user.InvalidNameException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@Embeddable
public class Name {
    private static final String NAME_REGEX = "^[ㄱ-ㅎ가-힣]{1,5}$";
    private String name;

    public Name(String name) {
        validateEmail(name);
    }

    protected Name() {
    }

    private void validateEmail(String name) {
        if(!Pattern.matches(NAME_REGEX, name)){
            throw new InvalidNameException();
        }
        this.name = name;
    }
}