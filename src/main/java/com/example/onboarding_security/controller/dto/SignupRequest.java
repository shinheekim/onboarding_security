package com.example.onboarding_security.controller.dto;

public record SignupRequest(
        String username,
        String password,
        String nickname
) {
}
