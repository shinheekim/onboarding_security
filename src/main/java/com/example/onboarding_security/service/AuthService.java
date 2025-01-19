package com.example.onboarding_security.service;

import com.example.onboarding_security.controller.dto.SignupRequest;
import com.example.onboarding_security.controller.dto.SignupResponse;
import com.example.onboarding_security.domain.User;
import com.example.onboarding_security.global.jwt.JwtUtil;
import com.example.onboarding_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.onboarding_security.domain.User.validateNickname;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("해당 유저 아이디가 존재합니다.");
        }
        validateNickname(request.nickname());

        User user = User.builder()
                .username(request.username())
                .password(request.password())
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
}
