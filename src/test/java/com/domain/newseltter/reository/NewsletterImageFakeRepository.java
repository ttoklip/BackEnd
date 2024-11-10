package com.domain.newseltter.reository;

import com.api.ttoklip.domain.newsletter.domain.NewsletterImage;
import com.api.ttoklip.domain.newsletter.repository.domain.NewsletterImageRepository;
import java.util.HashMap;

public class NewsletterImageFakeRepository implements NewsletterImageRepository {

    private final HashMap<Long, NewsletterImage> memoryRepository = new HashMap<>();
    private Long idCounter = 1L;

    @Override
    public NewsletterImage save(final NewsletterImage newsletterImage) {
        NewsletterImage savedNewsletterImage = NewsletterImage.builder()
                .id(idCounter)
                .url(newsletterImage.getUrl())
                .newsletter(newsletterImage.getNewsletter())
                .build();

        memoryRepository.put(idCounter, savedNewsletterImage);
        idCounter++;
        return savedNewsletterImage;
    }
}
