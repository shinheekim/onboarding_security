package com.example.onboarding_security.controller;

import com.example.onboarding_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/info")
    public ResponseEntity<String> retrieveUsername() {
        String username = userService.retrieveUsername();
        return new ResponseEntity<>(username, HttpStatus.OK);
    }
}
