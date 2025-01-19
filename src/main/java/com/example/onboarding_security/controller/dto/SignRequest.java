package com.example.onboarding_security.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record SignRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
