package com.codeforcode.redis.token;

import lombok.Data;

@Data
public class TokenRegisterRequest {
    private String userName;
    private String token;
}
