package com.example.onboarding_security.jwt;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
