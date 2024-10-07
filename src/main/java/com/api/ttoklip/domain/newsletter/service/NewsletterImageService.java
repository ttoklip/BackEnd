package com.api.ttoklip.domain.newsletter.service;

import com.api.ttoklip.domain.newsletter.domain.NewsletterImage;
import com.api.ttoklip.domain.newsletter.repository.NewsletterImageRepository;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
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
