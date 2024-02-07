package com.api.ttoklip.domain.newsletter.image.repository;

import com.api.ttoklip.domain.newsletter.image.domain.NewsletterImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterImageRepository extends JpaRepository<NewsletterImage, Long> {
}
