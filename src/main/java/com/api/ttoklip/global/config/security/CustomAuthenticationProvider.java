package com.api.ttoklip.global.config.security;

import com.api.ttoklip.domain.user.domain.repository.UserRepository;
import com.api.ttoklip.global.config.security.token.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String providerId = authentication.getCredentials().toString();

        UserDetails user = userRepository.findByProviderId(providerId)
                .map(UserPrincipal::createUser)
                .orElseThrow(() -> new UsernameNotFoundException("Provider ID not found: " + providerId));

        return new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
