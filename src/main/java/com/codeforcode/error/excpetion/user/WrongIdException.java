package com.codeforcode.error.excpetion.user;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class WrongIdException extends BusinessException {

    public WrongIdException() {
        super(ErrorMessage.WRONG_ID);
    }
}
