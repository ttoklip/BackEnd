package com.api.ttoklip.domain.newsletter.repository;

import com.api.ttoklip.domain.newsletter.domain.NewsletterUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterUrlRepository extends JpaRepository<NewsletterUrl, Long> {
}
