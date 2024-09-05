package com.api.ttoklip.domain.newsletter.post.repository;

import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long>, NewsletterQueryDslRepository {

}
