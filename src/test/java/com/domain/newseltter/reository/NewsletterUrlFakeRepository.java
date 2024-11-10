package com.domain.newseltter.reository;

import com.api.ttoklip.domain.newsletter.domain.NewsletterUrl;
import com.api.ttoklip.domain.newsletter.repository.domain.NewsletterUrlRepository;
import java.util.HashMap;

public class NewsletterUrlFakeRepository implements NewsletterUrlRepository {

    private final HashMap<Long, NewsletterUrl> memoryRepository = new HashMap<>();
    private Long idCounter = 1L;

    @Override
    public NewsletterUrl save(final NewsletterUrl newsletterUrl) {
        NewsletterUrl savedNewsletterUrl = NewsletterUrl.builder()
                .id(idCounter)
                .url(newsletterUrl.getUrl())
                .newsletter(newsletterUrl.getNewsletter())
                .build();

        memoryRepository.put(idCounter, savedNewsletterUrl);
        idCounter++;
        return savedNewsletterUrl;
    }
}
