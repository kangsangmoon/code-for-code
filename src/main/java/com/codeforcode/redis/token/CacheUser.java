package com.codeforcode.redis.token;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@RedisHash(value = "people", timeToLive = 30)
@AllArgsConstructor
public class CacheUser implements Serializable {
    @Id
    private Long id;
    private String name;
}