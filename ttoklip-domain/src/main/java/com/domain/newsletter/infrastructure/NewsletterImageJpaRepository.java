package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.NewsletterImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterImageJpaRepository extends JpaRepository<NewsletterImage, Long> {
}
