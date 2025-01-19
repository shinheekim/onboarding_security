package com.example.onboarding_security;

import com.example.onboarding_security.global.jwt.JwtUtil;
import com.example.onboarding_security.repository.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    private String secret;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil(userRepository);

        this.secret = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());

        // 리플렉션으로 private 필드 접근
        Field secretField = JwtUtil.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtUtil, this.secret);

        jwtUtil.init();
    }

    @Test
    void testSecretKeyInitialization() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        assertThat(decodedKey).isNotNull();
        assertThat(decodedKey.length).isGreaterThan(0);
    }

    @Test
    void testGenerateToken() {
        // Given
        Long userId = 12345L;
        Date now = new Date();

        // When
        String token = jwtUtil.generateAccessToken(userId, now);

        // Then
        assertThat(token).isNotEmpty();
    }

    @Test
    void testValidateToken() {
        // Given
        Long userId = 12345L;
        Date now = new Date();
        String token = jwtUtil.generateAccessToken(userId, now);

        // When & Then
        assertDoesNotThrow(() -> jwtUtil.validateToken(token));
    }
}
