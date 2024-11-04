package com.codeforcode.error.excpetion.auth;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class InvalidJwtException extends BusinessException {
    public InvalidJwtException() {
        super(ErrorMessage.INVALID_JWT);
    }
}
