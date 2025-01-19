//package com.project.popupmarket.config.handler;
//
//import com.project.popupmarket.config.jwt.TokenProvider;
//import com.project.popupmarket.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
//import com.project.popupmarket.entity.User;
//import com.project.popupmarket.repository.JwtTokenRepository;
//import com.project.popupmarket.service.user.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.Optional;
//
//@Component
//public class OAuth2SuccessHandler extends BaseAuthenticationSuccessHandler {
//    private static final Logger logger = LoggerFactory.getLogger(OAuth2SuccessHandler.class);
//    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
//    private final UserService userService;
//
//    public OAuth2SuccessHandler(TokenProvider tokenProvider,
//                                JwtTokenRepository jwtTokenRepository,
//                                OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository,
//                                UserService userService) {
//        super(tokenProvider, jwtTokenRepository);
//        this.authorizationRequestRepository = authorizationRequestRepository;
//        this.userService = userService;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException {
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        String email = oAuth2User.getAttribute("email");
//
//        if (email == null) {
//            logger.error("Google OAuth2 로그인: 이메일 정보를 찾을 수 없습니다.");
//            response.sendRedirect("/login?error=email_not_found");
//            return;
//        }
//
//        try {
//            Optional<User> userOptional = userService.findByEmail(email);
//            if (userOptional.isPresent()) {
//                processExistingUser(request, response, userOptional.get());
//            } else {
//                processNewUser(request, response, oAuth2User);
//            }
//        } catch (Exception ex) {
//            logger.error("Google OAuth2 로그인 처리 중 오류 발생", ex);
//            response.sendRedirect("/login?error=true");
//        } finally {
//            clearAuthenticationAttributes(request, response);
//        }
//    }
//
//    private void processExistingUser(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     User user) throws IOException {
//        // JWT 토큰 생성 및 저장
//        String refreshToken = tokenProvider.generateToken(user, JWT_TOKEN_DURATION);
//        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
//
//        saveJwtToken(user.getId(), refreshToken);
//        addJwtTokenToCookie(request, response, refreshToken);
//
//        String targetUrl = getTargetUrl(accessToken);
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//    }
//
//    private void processNewUser(HttpServletRequest request,
//                                HttpServletResponse response,
//                                OAuth2User oAuth2User) throws IOException {
//        String email = oAuth2User.getAttribute("email");
//        String name = oAuth2User.getAttribute("name");
//
//        try {
//            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
//            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
//
//
//            String targetUrl = UriComponentsBuilder.fromUriString("/register")
//                    .queryParam("email", encodedEmail)
//                    .queryParam("name", encodedName)
//                    .queryParam("oauth2", true)
//                    .build().toUriString();
//
//            getRedirectStrategy().sendRedirect(request, response, targetUrl);
//
//        } catch (UnsupportedEncodingException e) {
//            logger.error("URL 인코딩 오류", e);
//            response.sendRedirect("/login?error=encoding_error");
//        }
//    }
//
//    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
//        super.clearAuthenticationAttributes(request);
//        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
//    }
//}