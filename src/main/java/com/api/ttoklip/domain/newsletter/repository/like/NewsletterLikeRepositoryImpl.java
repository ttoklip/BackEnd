package com.api.ttoklip.domain.newsletter.repository.like;

import com.api.ttoklip.domain.newsletter.domain.NewsletterLike;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterLikeRepositoryImpl implements NewsletterLikeRepository {

    private final NewsletterLikeJpaRepository jpaRepository;
    private final NewsletterLikeQueryRepository queryDslRepository;

    @Override
    public Optional<NewsletterLike> findByNewsletterIdAndMemberId(final Long newsletterId, final Long memberId) {
        return jpaRepository.findByNewsletterIdAndMemberId(newsletterId, memberId);
    }

    @Override
    public boolean existsByNewsletterIdAndMemberId(final Long newsletterId, final Long memberId) {
        return jpaRepository.existsByNewsletterIdAndMemberId(newsletterId, memberId);
    }

    @Override
    public Long countNewsletterLikesByNewsletterId(final Long newsletterId) {
        return queryDslRepository.countNewsletterLikesByNewsletterId(newsletterId);
    }

    @Override
    public NewsletterLike save(final NewsletterLike newsletterLike) {
        return jpaRepository.saveAndFlush(newsletterLike);
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }
}
