package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.TodayNewsletter;
import com.domain.newsletter.domain.TodayNewsletterRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodayNewsletterRepositoryImpl implements TodayNewsletterRepository {

    private final TodayNewsletterJpaRepository jpaRepository;

    @Override
    public List<TodayNewsletter> findByCreatedDateBetween(final LocalDateTime startOfDay,
                                                          final LocalDateTime endOfDay) {

        return jpaRepository.findByCreatedDateBetween(startOfDay, endOfDay);
    }

    @Override
    public TodayNewsletter save(final TodayNewsletter todayNewsletter) {
        return jpaRepository.saveAndFlush(todayNewsletter);
    }
}
