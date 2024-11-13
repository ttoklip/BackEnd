package com.domain.newsletter.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsletterScrapRepository {

    Optional<NewsletterScrap> findByNewsletterIdAndMemberId(Long postId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long postId, Long memberId);

    Long countNewsletterScrapsByNewsletterId(final Long postId);

    NewsletterScrap save(NewsletterScrap newsletterScrap);

    void deleteById(Long postId);

    Page<Newsletter> getScrapPaging(Long memberId, Pageable pageable);
}
