package com.codeforcode.redis;

import com.codeforcode.user.domain.User;
import com.codeforcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, CacheUser> redisTemplate;
    private final UserRepository userRepository;

    public CacheUser save(TokenRegisterRequest request) {
        ValueOperations<String, CacheUser> operations = redisTemplate.opsForValue();
        String key = request.getToken();

        User user = userRepository
                .findById(request.getUserId())
                .get();

        CacheUser cacheUser = new CacheUser(user.getId(), user.getUserName());
        operations.set(key,cacheUser);

        return cacheUser;
    }

    public CacheUser find(String token){
        ValueOperations<String, CacheUser> operations = redisTemplate.opsForValue();
        CacheUser cacheUser = operations.get(token);

        return cacheUser;
    }
}
