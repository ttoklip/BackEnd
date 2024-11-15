package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterJpaRepository extends JpaRepository<Newsletter, Long> {

}
