package com.codeforcode.error.excpetion.user;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class DuplicateUserException extends BusinessException {
    public DuplicateUserException() {
        super(ErrorMessage.DUPLICATE_USERS_EXCEPTION);
    }
}