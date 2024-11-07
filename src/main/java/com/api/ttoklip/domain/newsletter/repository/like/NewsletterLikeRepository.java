package com.api.ttoklip.domain.newsletter.repository.like;

import com.api.ttoklip.domain.newsletter.domain.NewsletterLike;
import java.util.Optional;

public interface NewsletterLikeRepository {

    Optional<NewsletterLike> findByNewsletterIdAndMemberId(Long newsletterId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long newsletterId, Long memberId);

    Long countNewsletterLikesByNewsletterId(final Long newsletterId);

    NewsletterLike save(NewsletterLike newsletterLike);

    void deleteById(Long id);
}
