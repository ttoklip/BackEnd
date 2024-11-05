package com.api.ttoklip.domain.newsletter.repository.like;

import com.api.ttoklip.domain.newsletter.domain.NewsletterLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterLikeJpaRepository extends JpaRepository<NewsletterLike, Long> {

    Optional<NewsletterLike> findByNewsletterIdAndMemberId(Long newsletterId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long newsletterId, Long memberId);
}
