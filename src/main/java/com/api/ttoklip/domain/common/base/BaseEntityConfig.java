package com.api.ttoklip.domain.common.base;

import com.api.ttoklip.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class BaseEntityConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return Optional.of("Anonymous");
            }

            Member member = (Member) authentication.getPrincipal();
            String originName = member.getOriginName();
            return Optional.of(originName);
        };
    }
}
