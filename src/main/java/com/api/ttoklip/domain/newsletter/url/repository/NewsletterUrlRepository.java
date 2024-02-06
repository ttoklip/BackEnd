package com.api.ttoklip.domain.newsletter.url.repository;

import com.api.ttoklip.domain.newsletter.url.domain.NewsletterUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterUrlRepository extends JpaRepository<NewsletterUrl, Long> {
}
