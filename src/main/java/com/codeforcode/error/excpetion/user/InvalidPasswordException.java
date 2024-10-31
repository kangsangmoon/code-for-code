package com.codeforcode.error.excpetion.user;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ErrorMessage.INVALID_PASSWORD_REGEX);
    }
}
