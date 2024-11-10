package com.domain.newseltter.reository;

import com.api.ttoklip.domain.newsletter.domain.NewsletterScrap;
import com.api.ttoklip.domain.newsletter.repository.domain.NewsletterScrapRepository;
import java.util.HashMap;
import java.util.Optional;

public class NewsletterScrapFakeRepository implements NewsletterScrapRepository {

    private final HashMap<Long, NewsletterScrap> memoryRepository = new HashMap<>();
    private Long idCounter = 1L; // ID를 1부터 시작

    @Override
    public Optional<NewsletterScrap> findByNewsletterIdAndMemberId(final Long newsletterId, final Long memberId) {
        return memoryRepository.values().stream()
                .filter(newsletterScrap -> newsletterScrap.getNewsletter().getId().equals(newsletterId)
                        && newsletterScrap.getMember().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByNewsletterIdAndMemberId(final Long newsletterId, final Long memberId) {
        return memoryRepository.values().stream()
                .anyMatch(newsletterScrap -> newsletterScrap.getNewsletter().getId().equals(newsletterId)
                        && newsletterScrap.getMember().getId().equals(memberId));
    }

    @Override
    public Long countNewsletterScrapsByNewsletterId(final Long newsletterId) {
        return memoryRepository.values().stream()
                .filter(newsletterScrap -> newsletterScrap.getNewsletter().getId().equals(newsletterId))
                .count();
    }

    @Override
    public NewsletterScrap save(final NewsletterScrap newsletterScrap) {
        NewsletterScrap savedScrap = NewsletterScrap.builder()
                .id(idCounter++)  // ID 자동 증가
                .member(newsletterScrap.getMember())
                .newsletter(newsletterScrap.getNewsletter())
                .build();
        memoryRepository.put(savedScrap.getId(), savedScrap);
        return savedScrap;
    }

    @Override
    public void deleteById(final Long id) {
        memoryRepository.remove(id);
    }
}
