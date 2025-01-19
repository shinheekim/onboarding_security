package com.example.onboarding_security.jwt;

import com.example.onboarding_security.exception.CustomAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    @Value("${jwt.secret.key}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    // TODO: 우선 userId를 받을 예정
    public String generateToken(Long userId) {
        Date date = new Date();
        Date expiryDate = new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("JWT가 유효하지 않습니다.");
            throw new CustomAuthenticationException("JWT가 유효하지 않습니다.");
        } catch (ExpiredJwtException exception) {
            log.error("JWT가 만료되었습니다.");
            throw new CustomAuthenticationException("JWT가 유효하지 않습니다.");
        } catch (IllegalArgumentException exception) {
            log.error("JWT가 null이거나 비어 있거나 공백만 있습니다.");
            throw new CustomAuthenticationException("JWT가 유효하지 않습니다.");
        } catch (Exception exception) {
            log.error("JWT 검증에 실패했습니다.", exception);
            throw new CustomAuthenticationException("JWT가 유효하지 않습니다.");
        }
    }

    // TODO: 토큰 받아서 인증하는 메서드 추가
}
