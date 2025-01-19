package com.example.onboarding_security.controller;

import com.example.onboarding_security.domain.User;
import com.example.onboarding_security.mock.WithCustomMockUser;
import com.example.onboarding_security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .username("cinining")
                .password("password")
                .nickname("nickname")
                .build();

        userRepository.save(user);
    }

    @Test
    @WithCustomMockUser
    public void IfUserExistsThenGetUserInfoReturnsSuccess() throws Exception {
        mockMvc.perform(get("/user/info")
                        .header("X-AUTH-TOKEN", "aaaaaaa"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}