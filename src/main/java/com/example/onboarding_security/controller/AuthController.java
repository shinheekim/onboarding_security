package com.example.onboarding_security.controller;

import com.example.onboarding_security.controller.dto.SignRequest;
import com.example.onboarding_security.controller.dto.TokenResponse;
import com.example.onboarding_security.controller.dto.SignupRequest;
import com.example.onboarding_security.controller.dto.SignupResponse;
import com.example.onboarding_security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/sign")
    public ResponseEntity<TokenResponse> sign(@Valid @RequestBody SignRequest request) {
        TokenResponse response = authService.sign(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
