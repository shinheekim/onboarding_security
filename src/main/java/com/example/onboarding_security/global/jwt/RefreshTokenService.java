package com.example.onboarding_security.global.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenService {

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String username, String refreshToken) {
        redisTemplate.opsForValue().set(
                getKey(username),
                refreshToken,
                REFRESH_TOKEN_EXPIRE_TIME,
                TimeUnit.SECONDS
        );
    }

    public boolean validateRefreshToken(String username, String refreshToken) {
        String storedToken = redisTemplate.opsForValue().get(getKey(username));
        return refreshToken.equals(storedToken);
    }

    private String getKey(String username) {
        return "refreshToken:" + username;
    }
}
