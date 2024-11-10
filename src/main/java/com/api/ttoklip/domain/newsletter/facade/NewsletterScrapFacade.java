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
@Transactional(readOnly = true)
public class NewsletterScrapFacade implements ActionFacade {

    private final NewsletterScrapService newsletterScrapService;
    private final NewsletterPostService newsletterPostService;

    @Override
    @Transactional
    public Message register(final Long postId, final Long currentMemberId) {
        boolean exists = newsletterScrapService.isNewsletterExists(postId);
        if (!exists) {
            Newsletter newsletter = newsletterPostService.getNewsletter(postId);
            newsletterPostService.saveNewsletter(newsletter);
        }

        return Message.scrapPostSuccess(Newsletter.class, postId);
    }

    @Override
    @Transactional
    public Message cancel(final Long postId, final Long currentMemberId) {
        Newsletter newsletter = newsletterPostService.getNewsletter(postId);
        newsletterScrapService.cancelScrap(newsletter);
        return Message.scrapPostCancel(Newsletter.class, postId);
    }

}
