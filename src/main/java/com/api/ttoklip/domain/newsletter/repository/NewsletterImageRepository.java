package com.api.ttoklip.domain.newsletter.repository;

import com.api.ttoklip.domain.newsletter.domain.NewsletterImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterImageRepository extends JpaRepository<NewsletterImage, Long> {
}
