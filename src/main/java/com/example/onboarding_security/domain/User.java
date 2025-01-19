package com.example.onboarding_security.domain;

import com.example.onboarding_security.exception.InvalidNickNameException;
import jakarta.persistence.*;
import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = Role.ROLE_USER;
    }

    public User(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public static void validateNickname(String nickname) {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        if (!matcher.matches()) {
            throw new InvalidNickNameException("닉네임은 한글, 영문, 숫자만 입력 가능합니다.");
        }
        if (nickname.isEmpty()) {
            throw new InvalidNickNameException("닉네임은 1자 이상 입력해야 합니다.");
        }
    }
}
