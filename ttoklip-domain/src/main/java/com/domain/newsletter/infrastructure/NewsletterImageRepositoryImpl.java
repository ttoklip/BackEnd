package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.NewsletterImage;
import com.domain.newsletter.domain.NewsletterImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterImageRepositoryImpl implements NewsletterImageRepository {

    private final NewsletterImageJpaRepository jpaRepository;

    @Override
    public NewsletterImage save(final NewsletterImage newsletterImage) {
        return jpaRepository.save(newsletterImage);
    }
}
