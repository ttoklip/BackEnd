//package com.api.ttoklip.global.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@Profile("test")
//public class TestSecurityConfig {
//
//    @Bean(name = "testFilterChain")
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable();  // 테스트에서 CSRF 비활성화
//        return http.build();
//    }
//}
