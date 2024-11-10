package com.codeforcode.error.excpetion.example;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class ExampleNotFindByIdException extends BusinessException {
    public ExampleNotFindByIdException(){
        super(ErrorMessage.EXAMPLE_NOT_FIND_BY_ID);
    }
}