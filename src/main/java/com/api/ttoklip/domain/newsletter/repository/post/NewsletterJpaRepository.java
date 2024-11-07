package com.api.ttoklip.domain.newsletter.repository.post;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterJpaRepository extends JpaRepository<Newsletter, Long> {

}
