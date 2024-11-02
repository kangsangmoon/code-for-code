package com.codeforcode.redis;

import lombok.Data;

@Data
public class TokenRegisterRequest {
    private Long userId;
    private String token;
}
