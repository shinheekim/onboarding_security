package com.example.onboarding_security.service;

import com.example.onboarding_security.controller.dto.SignupRequest;
import com.example.onboarding_security.controller.dto.SignupResponse;
import com.example.onboarding_security.domain.User;
import com.example.onboarding_security.global.jwt.JwtUtil;
import com.example.onboarding_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("해당 유저 아이디가 존재합니다.");
        }

        User user = User.builder()
                .username(request.username())
                .password(request.password())
                .nickname(request.nickname())
                .build();
        userRepository.save(user);

        return SignupResponse.of(user.getUsername(), user.getNickname());
    }
}
