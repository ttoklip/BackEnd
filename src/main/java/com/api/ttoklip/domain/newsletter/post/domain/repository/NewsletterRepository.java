package com.api.ttoklip.domain.newsletter.post.domain.repository;

import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
}
