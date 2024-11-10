package com.domain.newseltter.reository;

import com.api.ttoklip.domain.newsletter.domain.TodayNewsletter;
import com.api.ttoklip.domain.newsletter.repository.domain.TodayNewsletterRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDayNewsletterFakeRepository implements TodayNewsletterRepository {

    private final Map<Long, TodayNewsletter> memoryRepository = new HashMap<>();
    private Long idCounter = 1L;  // ID를 1부터 시작

    // ToDo createDate 를 직접 정할 수 없으니 어떻게 해야할지 고민해봅시다..
    @Override
    public List<TodayNewsletter> findByCreatedDateBetween(final LocalDateTime startOfDay,
                                                          final LocalDateTime endOfDay) {
        return memoryRepository.values().stream()
                .filter(todayNewsletter ->
                        !todayNewsletter.getCreatedDate().isBefore(startOfDay) &&
                                !todayNewsletter.getCreatedDate().isAfter(endOfDay)
                )
                .toList();
    }

    @Override
    public TodayNewsletter save(final TodayNewsletter todayNewsletter) {
        TodayNewsletter savedNewsletter = TodayNewsletter.builder()
                .id(idCounter++)  // ID 자동 증가
                .newsletter(todayNewsletter.getNewsletter())
                .build();
        memoryRepository.put(savedNewsletter.getId(), savedNewsletter);
        return savedNewsletter;
    }
}
