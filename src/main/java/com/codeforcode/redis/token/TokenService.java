package com.codeforcode.redis.token;

import com.codeforcode.aop.annotation.Trace;
import com.codeforcode.user.dto.UserResponse;
import com.codeforcode.user.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    //private final TokenRepository tokenRepository;
    private final ValueOperations<String, String> valueOperations;
    private final UserRepository userRepository;

    public TokenService(RedisTemplate<String, String> redisTemplate, UserRepository userRepository) {
        this.redisTemplate = redisTemplate;
        //this.tokenRepository = tokenRepository;
        valueOperations = redisTemplate.opsForValue();
        this.userRepository = userRepository;
    }

    @Trace
    public String save(String token, String userName) {
        valueOperations.set(token, userName);

        return userName;
    }


    @Trace
    public String find(String token) {
        return valueOperations.get(token);
    }

    @Trace
    public UserResponse getUserResponseByToken(String token) {
        String userId = valueOperations.get(token);
        return userRepository.findByUserId(userId);
    }

    public void deleteRefreshToken(String token) {
        redisTemplate.delete(token);
    }
}