package com.codeforcode.redis.token;

import com.codeforcode.aop.annotation.Trace;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final RedisTemplate<String, Long> redisTemplate;
    private final TokenRepository tokenRepository;
    private final ValueOperations<String, Long> valueOperations;

    public TokenService(RedisTemplate<String, Long> redisTemplate, TokenRepository tokenRepository) {
        this.redisTemplate = redisTemplate;
        this.tokenRepository = tokenRepository;
        valueOperations = redisTemplate.opsForValue();
    }

    @Trace
    public Long save(String token,Long userId) {
        valueOperations.set(token, userId);

        return userId;
    }


    @Trace
    public Long find(String token){
        return valueOperations.get(token);
    }
}