package com.codeforcode.user.domain.vo;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class InvalidEmailException extends BusinessException {
    public InvalidEmailException() {
        super(ErrorMessage.INVALID_EMAIL_REGEX);
    }
}
