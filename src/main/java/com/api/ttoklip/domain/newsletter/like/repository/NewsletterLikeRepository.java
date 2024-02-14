package com.api.ttoklip.domain.newsletter.like.repository;

import com.api.ttoklip.domain.newsletter.like.entity.NewsletterLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsletterLikeRepository extends JpaRepository<NewsletterLike, Long>, NewsletterLikeRepositoryCustom {

    Optional<NewsletterLike> findByNewsletterIdAndMemberId(Long newsletterId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long postId, Long memberId);
}