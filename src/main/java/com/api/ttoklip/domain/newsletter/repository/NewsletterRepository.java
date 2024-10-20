package com.api.ttoklip.domain.newsletter.repository;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long>, NewsletterRepositoryCustom {

}
