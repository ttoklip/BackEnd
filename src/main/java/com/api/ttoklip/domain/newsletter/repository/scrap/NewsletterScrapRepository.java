package com.api.ttoklip.domain.newsletter.repository.scrap;

import com.api.ttoklip.domain.newsletter.domain.NewsletterScrap;
import java.util.Optional;

public interface NewsletterScrapRepository {

    Optional<NewsletterScrap> findByNewsletterIdAndMemberId(Long postId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long postId, Long memberId);

    Long countNewsletterScrapsByNewsletterId(final Long postId);

    NewsletterScrap save(NewsletterScrap newsletterScrap);

    void deleteById(Long postId);
}
