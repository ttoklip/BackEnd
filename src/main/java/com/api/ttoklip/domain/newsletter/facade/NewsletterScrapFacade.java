package com.api.ttoklip.domain.newsletter.facade;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.service.NewsletterPostService;
import com.api.ttoklip.domain.newsletter.service.NewsletterScrapService;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewsletterScrapFacade {

    private final NewsletterScrapService newsletterScrapService;
    private final NewsletterPostService newsletterPostService;

    @Transactional
    public Message registerScrap(Long postId) {
        boolean exists = newsletterScrapService.isNewsletterExists(postId);
        if (!exists) {
            Newsletter newsletter = newsletterPostService.getNewsletter(postId);
            newsletterPostService.saveNewsletter(newsletter);
        }

        return Message.scrapPostSuccess(Newsletter.class, postId);
    }

    @Transactional
    public Message cancelScrap(Long postId) {
        Newsletter newsletter = newsletterPostService.getNewsletter(postId);
        newsletterScrapService.cancelScrap(newsletter);
        return Message.scrapPostCancel(Newsletter.class, postId);
    }

}
