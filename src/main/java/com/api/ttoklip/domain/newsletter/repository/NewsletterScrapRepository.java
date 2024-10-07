package com.api.ttoklip.domain.newsletter.repository;

import com.api.ttoklip.domain.newsletter.domain.NewsletterScrap;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterScrapRepository extends JpaRepository<NewsletterScrap, Long>,
        NewsletterScrapRepositoryCustom {
    Optional<NewsletterScrap> findByNewsletterIdAndMemberId(Long newsletterId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long postId, Long memberId);
}
