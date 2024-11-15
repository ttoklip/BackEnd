package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.NewsletterUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterUrlJpaRepository extends JpaRepository<NewsletterUrl, Long> {
}
