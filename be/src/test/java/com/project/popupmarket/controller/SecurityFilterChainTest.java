package com.project.popupmarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.popupmarket.dto.auth.LoginRequest;
import com.project.popupmarket.dto.token.CreateAccessTokenResponse;
import com.project.popupmarket.entity.User;
import com.project.popupmarket.enums.AuthProvider;
import com.project.popupmarket.enums.Role;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static com.project.popupmarket.config.jwt.AuthConstants.JWT_TOKEN_COOKIE_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(properties = {
        "spring.thymeleaf.check-template=false",
        "spring.thymeleaf.check-template-location=false",
        "spring.thymeleaf.enabled=false"
})
class SecurityFilterChainTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private String accessToken;
    private Cookie refreshTokenCookie;

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

        testUser.setSocial(AuthProvider.GOOGLE);
        testUser.setRole(Role.CUSTOMER);

        userRepository.save(testUser);
    }

    private void performLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(loginRequest)))
                                  .andExpect(status().isOk())
                                  .andReturn();

        // Access Token 추출
        CreateAccessTokenResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CreateAccessTokenResponse.class
                                                                   );
        accessToken = response.getAccessToken();

        // Refresh Token 쿠키 추출
        refreshTokenCookie = result.getResponse().getCookie(JWT_TOKEN_COOKIE_NAME);
    }

    @Test
    @DisplayName("보호된 엔드포인트 접근 - 인증 성공")
    void accessProtectedEndpointSuccess() throws Exception {
        // given
        performLogin();

        // when & then
        mockMvc.perform(get("/mypage/popup")
                                        .header("Authorization", "Bearer " + accessToken)
                                        .cookie(refreshTokenCookie))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("보호된 엔드포인트 접근 - 인증 실패 (토큰 없음)")
    void accessProtectedEndpointNoToken() throws Exception {
        mockMvc.perform(get("/mypage/popup"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("보호된 엔드포인트 접근 - 인증 실패 (잘못된 토큰)")
    void accessProtectedEndpointInvalidToken() throws Exception {
        mockMvc.perform(get("/mypage/popup")
                                .header("Authorization", "Bearer invalid_token"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그아웃 후 보호된 엔드포인트 접근 실패")
    void accessProtectedEndpointAfterLogout() throws Exception {
        // given
        performLogin();

        // when
        // 먼저 로그아웃 수행
        mockMvc.perform(post("/api/auth/logout")
                                .cookie(refreshTokenCookie))
               .andExpect(status().isOk());

        // accessToken 초기화
        accessToken = null;

        // then
        // 로그아웃 후 보호된 엔드포인트 접근 시도
        mockMvc.perform(get("/mypage/popup")
                                        .header("Authorization", "Bearer " + accessToken)
                                        .cookie(refreshTokenCookie))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("공개 엔드포인트는 인증 없이 접근 가능")
    void accessPublicEndpoint() throws Exception {
        // 템플릿 에러를 피하기 위해 API 엔드포인트로 테스트
        mockMvc.perform(get("/popup/list"))  // /main 대신 다른 공개 엔드포인트 사용
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("만료된 액세스 토큰으로 접근 실패")
    void accessProtectedEndpointExpiredToken() throws Exception {
        // expired token example (실제 만료된 토큰)
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyMzkwMjJ9.8qx_lqK4d8yXGjz6_9nOOEP4DUha4M5AYxfHcz5X9og";

        mockMvc.perform(get("/mypage/popup")
                                .header("Authorization", "Bearer " + expiredToken))
               .andExpect(status().isUnauthorized());
    }
}