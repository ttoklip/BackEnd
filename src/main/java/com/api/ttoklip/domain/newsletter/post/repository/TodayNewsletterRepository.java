package com.api.ttoklip.domain.newsletter.post.repository;

import com.api.ttoklip.domain.newsletter.post.domain.TodayNewsletter;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayNewsletterRepository extends JpaRepository<TodayNewsletter, Long> {
    @EntityGraph(attributePaths = {"newsletter"})
    List<TodayNewsletter> findByCreatedDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
