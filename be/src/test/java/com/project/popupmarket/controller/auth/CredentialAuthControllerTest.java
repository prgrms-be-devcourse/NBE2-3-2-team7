package com.project.popupmarket.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.popupmarket.config.jwt.TokenProvider;
import com.project.popupmarket.dto.auth.LoginRequest;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.repository.UserRepository;
import com.project.popupmarket.service.token.TokenService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static com.project.popupmarket.config.jwt.AuthConstants.JWT_TOKEN_COOKIE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CredentialAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TokenProvider tokenProvider;

    private User testUser;
    private LoginRequest loginRequest;
    @Autowired
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        testUser = User
                .builder()
                .email("test@example.com")
                .password(passwordEncoder.encode("password123"))
                .name("Test User")
                .brand("Test Brand")
                .tel("010-1234-5678")
                .build();

        userRepository.save(testUser);
        loginRequest = new LoginRequest("test@example.com", "password123");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws Exception {
        MvcResult result = mockMvc
                .perform(post("/api/auth/login")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists(JWT_TOKEN_COOKIE_NAME))
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn();

        Cookie refreshTokenCookie = result
                .getResponse()
                .getCookie(JWT_TOKEN_COOKIE_NAME);
        System.out.println("refreshTokenCookie = " + refreshTokenCookie.getValue());

        String cachedToken = tokenService.findRefreshToken(testUser.getId());
        System.out.println("cachedToken = " + cachedToken);

        assertThat(cachedToken).isEqualTo(refreshTokenCookie.getValue());
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void loginFailWrongPassword() throws Exception {
        LoginRequest wrongPasswordRequest = new LoginRequest("test@example.com", "wrongpassword");

        mockMvc
                .perform(post("/api/auth/login")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(wrongPasswordRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    void logoutSuccess() throws Exception {
        // 로그인
        MvcResult loginResult = mockMvc
                .perform(post("/api/auth/login")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();

        Cookie refreshTokenCookie = loginResult
                .getResponse()
                .getCookie(JWT_TOKEN_COOKIE_NAME);

        // 로그아웃
        mockMvc
                .perform(post("/api/auth/logout").cookie(refreshTokenCookie))
                .andExpect(status().isOk());

        String cachedToken = tokenService.findRefreshToken(testUser.getId());
        assertThat(cachedToken).isNull();
    }

    @Test
    @DisplayName("로그아웃 실패 - 쿠키 없음")
    void logoutFailNoCookie() throws Exception {
        mockMvc
                .perform(post("/api/auth/logout"))
                .andExpect(status().isBadRequest());
    }
}