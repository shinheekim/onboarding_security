package com.example.onboarding_security.controller;

import com.example.onboarding_security.controller.dto.*;
import com.example.onboarding_security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "회원가입 및 로그인, 토큰 재발금 API 제공")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이름, 비밀번호, 닉네임을 입력받아 회원가입하는 API")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "로그인", description = "이름, 비밀번호를 입력받아 로그인하는 API")
    @PostMapping("/sign")
    public ResponseEntity<TokenResponse> sign(@Valid @RequestBody SignRequest request) {
        TokenResponse response = authService.sign(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 입력받아 엑세스 토큰을 재발급하는 API")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenResponse response = authService.refreshAccessToken(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
