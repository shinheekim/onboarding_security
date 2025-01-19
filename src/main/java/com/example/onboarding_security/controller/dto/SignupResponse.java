package com.example.onboarding_security.controller.dto;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Builder
public record SignupResponse(
        String username,
        String nickname,
        List<GrantedAuthority> authorities
) {
    public static SignupResponse of(String username, String nickname) {
        return SignupResponse.builder()
                .username(username)
                .nickname(nickname)
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }
}

