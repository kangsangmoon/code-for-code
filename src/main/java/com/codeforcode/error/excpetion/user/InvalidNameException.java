package com.codeforcode.error.excpetion.user;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class InvalidNameException extends BusinessException {
    public InvalidNameException() {
        super(ErrorMessage.INVALID_NAME_REGEX);
    }
}
