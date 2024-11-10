package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.NewsletterUrl;
import com.domain.newsletter.domain.NewsletterUrlRepository;
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
