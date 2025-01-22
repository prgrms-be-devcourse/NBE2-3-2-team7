package com.project.popupmarket.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.popupmarket.dto.auth.LoginRequest;
import com.project.popupmarket.dto.token.CreateAccessTokenResponse;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.enums.AuthProvider;
import com.project.popupmarket.enums.Role;
import com.project.popupmarket.repository.JwtTokenRepository;
import com.project.popupmarket.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static com.project.popupmarket.config.handler.BaseAuthenticationSuccessHandler.JWT_TOKEN_COOKIE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        // 테스트 사용자 생성
        testUser = User.builder()
                       .email("test@example.com")
                       .password(passwordEncoder.encode("password123"))
                       .name("Test User")
                       .brand("Test Brand")
                       .tel("010-1234-5678")
                       .build();

        // 필수 필드 설정
        testUser.setSocial(AuthProvider.GOOGLE);  // 로컬 로그인 사용자
        testUser.setRole(Role.CUSTOMER);  // 일반 사용자 권한

        userRepository.save(testUser);

        loginRequest = new LoginRequest("test@example.com", "password123");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws Exception {
        // when
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(loginRequest)))
                                  .andExpect(status().isOk())
                                  .andExpect(cookie().exists(JWT_TOKEN_COOKIE_NAME))
                                  .andExpect(jsonPath("$.accessToken").exists())
                                  .andReturn();

        // then
        CreateAccessTokenResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CreateAccessTokenResponse.class
                                                                   );

        // JWT 토큰이 DB에 저장되었는지 확인
        Cookie refreshTokenCookie = result.getResponse().getCookie(JWT_TOKEN_COOKIE_NAME);
        assertThat(refreshTokenCookie).isNotNull();
        assertThat(jwtTokenRepository.findByUserId(testUser.getId())).isPresent();
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void loginFailWrongPassword() throws Exception {
        // given
        LoginRequest wrongPasswordRequest = new LoginRequest("test@example.com", "wrongpassword");

        // when & then
        mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(wrongPasswordRequest)))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그아웃 성공 테스트")
    void logoutSuccess() throws Exception {
        // given: 먼저 로그인
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(loginRequest)))
                                       .andReturn();

        Cookie refreshTokenCookie = loginResult.getResponse().getCookie(JWT_TOKEN_COOKIE_NAME);

        // when & then
        mockMvc.perform(post("/api/auth/logout")
                                .cookie(refreshTokenCookie))
               .andExpect(status().isOk());

        // DB에서 리프레시 토큰이 삭제되었는지 확인
        assertThat(jwtTokenRepository.findByUserId(testUser.getId())).isEmpty();
    }

    @Test
    @DisplayName("로그아웃 실패 - 쿠키 없음")
    void logoutFailNoCookie() throws Exception {
        // when & then
        mockMvc.perform(post("/api/auth/logout"))
               .andExpect(status().isBadRequest());
    }
}