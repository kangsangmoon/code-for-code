package com.codeforcode.error.excpetion.user;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class LoginFailException extends BusinessException {
    public LoginFailException() {
        super(ErrorMessage.LOGIN_FAIL);
    }
}
