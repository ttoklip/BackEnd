package com.api.ttoklip.domain.newsletter.scarp.repository;

import com.api.ttoklip.domain.newsletter.scarp.entity.NewsletterScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsletterScrapRepository extends JpaRepository<NewsletterScrap, Long>, NewsletterScrapRepositoryCustom {
    Optional<NewsletterScrap> findByNewsletterIdAndMemberId(Long newsletterId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long postId, Long memberId);
}
