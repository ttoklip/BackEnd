package com.api.ttoklip.global.config;

//import com.api.ttoklip.global.security.auth.handler.TokenErrorHandler;

import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.global.security.jwt.JwtAuthenticationFilter;
import com.api.ttoklip.global.security.oauth2.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    //    private final TokenErrorHandler tokenErrorHandler;
    private final CustomAuthenticationEntryPoint entryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors()
                .and()
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/favicon.ico"
                                        , "/health"
                                        , "/swagger-ui/**"
                                        , "/v3/api-docs/**"
                                        , "/api/v1/auth/**"
                                        , "/api/v1/oauth"
                                        , "/error"
                                        , "/api/v1/email/**"
                                        , "/api/v1/privacy/local/check-nickname"
                                ).permitAll()
                                .requestMatchers(
                                        "/api/v1/admin/**"
                                )
                                .hasAnyRole(Role.MANAGER.name())
                                .anyRequest().authenticated());
//        http.exceptionHandling(e -> e.accessDeniedHandler(tokenErrorHandler));
        http.exceptionHandling()
                .authenticationEntryPoint(entryPoint);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addExposedHeader("Authorization");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
