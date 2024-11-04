package com.codeforcode.redis.token;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.user.domain.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final TokenRepository tokenRepository;
    private final ValueOperations<String, String> valueOperations;

    public TokenService(RedisTemplate<String, String> redisTemplate, TokenRepository tokenRepository) {
        this.redisTemplate = redisTemplate;
        this.tokenRepository = tokenRepository;
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Trace
    public String save(String token, String userName) {
        valueOperations.set(token, userName);

        return userName;
    }


    @Trace
    public String find(String token){
        return valueOperations.get(token);
    }
}