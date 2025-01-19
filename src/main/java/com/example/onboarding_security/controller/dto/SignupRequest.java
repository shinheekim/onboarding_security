package com.example.onboarding_security.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank(message = "닉네임은 비어 있을 수 없습니다.")
        @Size(min = 1, message = "닉네임은 1자 이상 입력해야 합니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$", message = "닉네임은 한글, 영문, 숫자만 입력 가능합니다.")
        String nickname
) {
}
