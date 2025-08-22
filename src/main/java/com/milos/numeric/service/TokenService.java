package com.milos.numeric.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private final long duration = 10;
    private final RedisTemplate<String, String> redisTemplate;

    public TokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createToken(String email) {
        String token = UUID.randomUUID().toString();
        this.redisTemplate.opsForValue().set(token, email, duration, TimeUnit.MINUTES);
        return token;
    }

    public String getEmailByToken(String token) {
        return this.redisTemplate.opsForValue().get(token);
    }

    public boolean deleteToken(String token) {
        return this.redisTemplate.delete(token);
    }

    public boolean isValid(String token) {
        return this.redisTemplate.hasKey(token);
    }
}
