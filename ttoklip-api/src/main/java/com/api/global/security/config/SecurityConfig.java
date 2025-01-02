package com.api.global.security.config;

import com.api.global.security.SecurityConstants;
import com.api.global.security.handler.AuthAccessDeniedHandler;
import com.api.global.security.handler.AuthEntryPointHandler;
import com.api.global.security.filter.JwtAuthenticationFilter;
import com.domain.member.domain.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthEntryPointHandler entryPoint;
    private final AuthAccessDeniedHandler deniedHandler;

    @Bean
    public SecurityFilterChain filterChain(
            final HttpSecurity http
    ) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors()
                .and()
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        SecurityConstants.PUBLIC_ENDPOINTS
                                ).permitAll()
                                .requestMatchers(
                                        SecurityConstants.ADMIN_ENDPOINTS
                                ).hasAnyRole(Role.MANAGER.name())
                                .requestMatchers(
                                        HttpMethod.POST, SecurityConstants.MANAGER_POST_ENDPOINTS
                                ).hasAnyRole(Role.MANAGER.name())
                                .anyRequest().authenticated()
                );

        http.exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(deniedHandler);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
