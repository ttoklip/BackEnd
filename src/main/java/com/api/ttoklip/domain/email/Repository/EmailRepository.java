package com.api.ttoklip.domain.email.Repository;

import com.api.ttoklip.domain.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    public Optional<Email> findByEmail(String email);
}
