package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.TodayNewsletter;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayNewsletterJpaRepository extends JpaRepository<TodayNewsletter, Long> {

    @EntityGraph(attributePaths = {"newsletter"})
    List<TodayNewsletter> findByCreatedDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
