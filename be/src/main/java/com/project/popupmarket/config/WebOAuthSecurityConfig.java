package com.project.popupmarket.config;

import com.project.popupmarket.config.jwt.TokenAuthenticationFilter;
import com.project.popupmarket.config.jwt.TokenProvider;
import com.project.popupmarket.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.project.popupmarket.service.oauth.OAuth2UserCustomService;
import com.project.popupmarket.service.user.UserDetailService;
import com.project.popupmarket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final UserDetailService userDetailService;
    private final UserService userService;

    // WebSecurityCustomizer 빈 등록
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // SecurityFilterChain 빈 등록
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // API 요청에 대한 권한 설정
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/api/signup", "/api/login")
                .permitAll()
                .requestMatchers("/api/**")
                .permitAll() // 추후 수정

                // 누구나 접근 가능한 페이지
                .requestMatchers(antMatcher("/"))
                .permitAll()
                .requestMatchers(antMatcher("/main"))
                .permitAll()
                .requestMatchers(antMatcher("/popup/list"))
                .permitAll()
                .requestMatchers(antMatcher("/rental/list"))
                .permitAll()
                .requestMatchers(antMatcher("/rental/detail/**"))
                .permitAll()
                .requestMatchers(antMatcher("/popup/detail/**"))
                .permitAll()
                .requestMatchers(antMatcher("/register/**"))
                .permitAll()
                .requestMatchers("/oauth2/**")
                .permitAll()
                .requestMatchers("/login/oauth2/**")
                .permitAll()

                // 로그인 필요한 페이지
                .requestMatchers(antMatcher("/mypage/**"))
                .authenticated()
                .requestMatchers(antMatcher("/payment"))
                .authenticated()

                .anyRequest()
                .permitAll());

        // 폼 로그인 설정
        //        http.formLogin(formLogin -> formLogin
        //                .loginPage("/login")
        //                .usernameParameter("email")
        //                .passwordParameter("password")
        //                .loginProcessingUrl("/login")
        //                .successHandler(formLoginSuccessHandler())
        //                .permitAll()
        //        );

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .authorizationEndpoint(authorization -> authorization.authorizationRequestRepository(
                        oAuth2AuthorizationRequestBasedOnCookieRepository()))
                .defaultSuccessUrl("/api/auth/oauth2/callback")
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserCustomService)));

        http.exceptionHandling(exception -> exception.defaultAuthenticationEntryPointFor(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**")));

        return http.build();
    }

    // OAuth2 로그인 성공 핸들러 추가
    //    @Bean
    //    public OAuth2SuccessHandler oAuth2SuccessHandler() {
    //        return new OAuth2SuccessHandler(tokenProvider, jwtTokenRepository,
    //                                        oAuth2AuthorizationRequestBasedOnCookieRepository(), userService);
    //    }

    // 폼 로그인 성공 핸들러 추가
    //    @Bean
    //    public FormLoginSuccessHandler formLoginSuccessHandler() {
    //        return new FormLoginSuccessHandler(tokenProvider, jwtTokenRepository);
    //    }

    // TokenAuthenticationFilter 빈 등록
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    // OAuth2AuthorizationRequestBasedOnCookieRepository 빈 등록
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    // AuthenticationManager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    // BCryptPasswordEncoder 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}