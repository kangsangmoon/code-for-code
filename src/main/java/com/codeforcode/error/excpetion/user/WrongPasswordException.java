package com.codeforcode.error.excpetion.user;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class WrongPasswordException extends BusinessException {

    public WrongPasswordException() {
        super(ErrorMessage.WRONG_PASSWORD);
    }
}
