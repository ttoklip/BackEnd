package com.api.ttoklip.domain.newsletter.url.service;

import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.url.domain.NewsletterUrl;
import com.api.ttoklip.domain.newsletter.url.repository.NewsletterUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterUrlService {

    private final NewsletterUrlRepository newsletterUrlRepository;

    @Transactional
    public void register(final Newsletter newsletter, final String url) {
        NewsletterUrl newsletterUrl = NewsletterUrl.of(newsletter, url);
        newsletterUrlRepository.save(newsletterUrl);
    }
}
