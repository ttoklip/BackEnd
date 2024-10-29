package com.api.ttoklip.domain.newsletter.repository.scrap;

import com.api.ttoklip.domain.newsletter.domain.NewsletterScrap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterScrapRepositoryImpl implements NewsletterScrapRepository {

    private final NewsletterScrapJpaRepository jpaScrapRepository;
    private final NewsletterScrapQueryRepository queryRepository;

    @Override
    public Optional<NewsletterScrap> findByNewsletterIdAndMemberId(final Long postId, final Long memberId) {
        return jpaScrapRepository.findByNewsletterIdAndMemberId(postId, memberId);
    }

    @Override
    public boolean existsByNewsletterIdAndMemberId(final Long postId, final Long memberId) {
        return jpaScrapRepository.existsByNewsletterIdAndMemberId(postId, memberId);
    }

    @Override
    public Long countNewsletterScrapsByNewsletterId(final Long postId) {
        return queryRepository.countNewsletterScrapsByNewsletterId(postId);
    }

    @Override
    public NewsletterScrap save(final NewsletterScrap newsletterScrap) {
        return jpaScrapRepository.save(newsletterScrap);
    }

    @Override
    public void deleteById(final Long postId) {
        jpaScrapRepository.deleteById(postId);
    }
}
