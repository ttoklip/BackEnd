package com.api.ttoklip.domain.newsletter.repository.today;

import com.api.ttoklip.domain.newsletter.domain.TodayNewsletter;
import java.time.LocalDateTime;
import java.util.List;

public interface TodayNewsletterRepository {

    List<TodayNewsletter> findByCreatedDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
