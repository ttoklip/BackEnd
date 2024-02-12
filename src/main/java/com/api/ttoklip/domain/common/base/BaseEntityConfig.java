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
                return Optional.of("AnonymousNULL");
            }

            System.out.println("authentication = " + authentication);
            System.out.println("----------- 클래스 타입" + authentication.getClass());
            System.out.println("----------- 클래스 타입" + authentication.getPrincipal().getClass());


            Object principal = authentication.getPrincipal();
            if (principal instanceof Member) {
                Member member = (Member) principal;
                String email = member.getEmail();
                return Optional.ofNullable(email);
            } else {
                // principal이 Member 타입이 아닌 경우의 처리
                return Optional.of("AnonymousNOT_TYPE");
            }

        };
    }
}
