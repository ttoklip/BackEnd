package com.api.ttoklip.domain.newsletter.repository.image;

import com.api.ttoklip.domain.newsletter.domain.NewsletterImage;
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
