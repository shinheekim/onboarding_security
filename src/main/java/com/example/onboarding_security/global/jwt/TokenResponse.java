package com.example.onboarding_security.global.jwt;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
