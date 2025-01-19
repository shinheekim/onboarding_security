package com.example.onboarding_security.domain;

import com.example.onboarding_security.exception.InvalidNickNameException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣]*$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private void validateNickname(String nickname) {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        if (!matcher.matches()) {
            throw new InvalidNickNameException("닉네임은 한글, 영문, 숫자만 입력 가능합니다.");
        }
        if (nickname.isEmpty()) {
            throw new InvalidNickNameException("닉네임은 1자 이상 입력해야 합니다.");
        }
    }
}
