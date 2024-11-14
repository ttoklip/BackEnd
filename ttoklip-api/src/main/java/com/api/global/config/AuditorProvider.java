package com.api.global.config;

import com.domain.member.domain.Member;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@Configuration
public class AuditorProvider {

    @Bean("auditorProvider2")
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return Optional.of("AnonymousNULL");
            }

            Object principal = authentication.getPrincipal();
            if (principal instanceof Member member) {
                String email = member.getEmail();
                return Optional.ofNullable(email);
            }
            return Optional.of("AnonymousNOT_TYPE");
        };
    }
}
