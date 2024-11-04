package com.codeforcode.error.excpetion.redis;

import com.codeforcode.error.BusinessException;
import com.codeforcode.error.dto.ErrorMessage;

public class RedisErrorException extends BusinessException {
    public RedisErrorException() {
        super(ErrorMessage.REDIS_ERROR);
    }
}
