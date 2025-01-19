package com.example.onboarding_security.global.error;

public record CustomErrorResponse(
        int status,
        String message
) {
}
