package com.api.ttoklip.domain.newsletter.post.repository;

import com.api.ttoklip.domain.newsletter.post.domain.TodayNewsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayNewsletterRepository extends JpaRepository<TodayNewsletter, Long> {
}
