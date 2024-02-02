package com.api.ttoklip.domain.auth.service;

import com.api.ttoklip.domain.user.domain.User;
import com.api.ttoklip.domain.user.domain.repository.UserRepository;
import com.api.ttoklip.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return UserPrincipal.createUser(user.get());
        }

        throw new UsernameNotFoundException("유효하지 않는 유저입니다.");
    }

    public UserDetails loadUserById(Long id) {
        log.debug("Attempting to load user by ID: {}", id);
        return userRepository.findById(id)
                .map(UserPrincipal::createUser)
                .orElseThrow(() -> {
                    log.error("User with ID: {} could not be found.", id);
                    return new UsernameNotFoundException("유효하지 않는 유저입니다.");
                });
    }
}
