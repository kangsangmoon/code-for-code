package com.codeforcode.redis.token;

import lombok.Data;

@Data
public class TokenRegisterRequest {
    private Long userId;
    private String token;
}
