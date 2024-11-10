package com.domain.newsletter.application;

import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterImage;
import com.domain.newsletter.domain.NewsletterImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsletterImageService {

    private final NewsletterImageRepository newsletterImageRepository;

    @Transactional
    public void register(final Newsletter newsletter, final String url) {
        NewsletterImage newsletterImage = NewsletterImage.of(newsletter, url);
        newsletterImageRepository.save(newsletterImage);
    }
}
