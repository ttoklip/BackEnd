package com.domain.newsletter.application;

import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterUrl;
import com.domain.newsletter.domain.NewsletterUrlRepository;
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
