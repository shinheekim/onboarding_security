package com.example.onboarding_security.service;

import com.example.onboarding_security.controller.dto.*;
import com.example.onboarding_security.domain.User;
import com.example.onboarding_security.global.exception.CustomAuthenticationException;
import com.example.onboarding_security.global.jwt.JwtUtil;
import com.example.onboarding_security.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.onboarding_security.domain.User.validateNickname;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("해당 유저 아이디가 존재합니다.");
        }
        validateNickname(request.nickname());

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build();
        userRepository.save(user);

        return SignupResponse.of(user.getUsername(), user.getNickname());
    }

    public TokenResponse sign(SignRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 아이디가 존재하지 않습니다."));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        TokenResponse response = jwtUtil.generateTokens(user.getId());
        return response;
    }

    public TokenResponse refreshAccessToken(@Valid RefreshTokenRequest request) {
        String username = jwtUtil.getUsernameFromToken(request.refreshToken());

        // Redis에서 RefreshToken 조회 및 검증
        String storedToken = redisTemplate.opsForValue().get("refreshToken:" + username).toString();
        if (storedToken == null || !storedToken.equals(request.refreshToken())) {
            throw new CustomAuthenticationException("Refresh Token이 유효하지 않습니다.");
        }

        // 새로운 Access Token 생성
        String newAccessToken = jwtUtil.generateAccessToken(username, new Date());
        return new TokenResponse(newAccessToken, request.refreshToken());
    }
}
