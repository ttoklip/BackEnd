package com.api.ttoklip.domain.newsletter.repository.url;

import com.api.ttoklip.domain.newsletter.domain.NewsletterUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterUrlRepositoryImpl implements NewsletterUrlRepository {

    private final NewsletterUrlJpaRepository jpaRepository;

    @Override
    public NewsletterUrl save(final NewsletterUrl newsletterUrl) {
        return jpaRepository.saveAndFlush(newsletterUrl);
    }
}
