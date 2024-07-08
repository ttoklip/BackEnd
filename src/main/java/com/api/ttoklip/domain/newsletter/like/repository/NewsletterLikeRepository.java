package com.api.ttoklip.domain.newsletter.like.repository;

import com.api.ttoklip.domain.newsletter.like.entity.NewsletterLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterLikeRepository extends JpaRepository<NewsletterLike, Long>, NewsletterLikeRepositoryCustom {

    Optional<NewsletterLike> findByNewsletterIdAndMemberId(Long newsletterId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long newsletterId, Long memberId);
}
