package com.domain.newsletter.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface TodayNewsletterRepository {

    List<TodayNewsletter> findByCreatedDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    TodayNewsletter save(TodayNewsletter todayNewsletter);
}
