package com.domain.newseltter.reository;

import com.api.ttoklip.domain.newsletter.domain.NewsletterLike;
import com.api.ttoklip.domain.newsletter.repository.domain.NewsletterLikeRepository;
import java.util.HashMap;
import java.util.Optional;

public class NewsletterLikeFakeRepository implements NewsletterLikeRepository {

    private final HashMap<Long, NewsletterLike> memoryRepository = new HashMap<>();
    private Long idCounter = 0L;

    @Override
    public Optional<NewsletterLike> findByNewsletterIdAndMemberId(final Long newsletterId, final Long memberId) {
        return memoryRepository.values().stream()
                .filter(newsletterLike -> newsletterLike.getNewsletter().getId().equals(newsletterId)
                        && newsletterLike.getMember().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByNewsletterIdAndMemberId(final Long newsletterId, final Long memberId) {
        return memoryRepository.values().stream()
                .anyMatch(newsletterLike -> newsletterLike.getNewsletter().getId().equals(newsletterId)
                        && newsletterLike.getMember().getId().equals(memberId));
    }

    @Override
    public Long countNewsletterLikesByNewsletterId(final Long newsletterId) {
        return memoryRepository.values().stream()
                .filter(newsletterLike -> newsletterLike.getNewsletter().getId().equals(newsletterId))
                .count();
    }

    @Override
    public NewsletterLike save(final NewsletterLike newsletterLike) {
        NewsletterLike saveNewsletterLike = NewsletterLike.builder()
                .id(idCounter)
                .member(newsletterLike.getMember())
                .newsletter(newsletterLike.getNewsletter())
                .build();
        idCounter++;
        return saveNewsletterLike;
    }

    @Override
    public void deleteById(final Long id) {
        memoryRepository.remove(id);
    }
}
