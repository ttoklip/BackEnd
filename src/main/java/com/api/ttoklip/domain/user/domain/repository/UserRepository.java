package com.api.ttoklip.domain.user.domain.repository;

import com.api.ttoklip.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByProviderId(Long providerId);
}
