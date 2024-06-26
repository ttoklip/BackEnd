package com.api.ttoklip.domain.email.Repository;

import com.api.ttoklip.domain.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    public Optional<Email> findByEmail(String email);
}
