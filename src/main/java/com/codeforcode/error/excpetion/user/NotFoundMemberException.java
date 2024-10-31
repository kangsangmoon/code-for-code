package com.codeforcode.error.excpetion.user;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class NotFoundMemberException extends BusinessException {
    public NotFoundMemberException() {
        super(ErrorMessage.NOT_FOUND_USERS_EXCEPTION);
    }
}