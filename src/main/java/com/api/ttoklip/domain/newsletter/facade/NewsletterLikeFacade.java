package com.api.ttoklip.domain.newsletter.facade;

import com.api.ttoklip.domain.common.ActionFacade;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.service.NewsletterLikeService;
import com.api.ttoklip.domain.newsletter.service.NewsletterPostService;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterLikeFacade implements ActionFacade {

    private final NewsletterLikeService newsletterLikeService;
    private final NewsletterPostService newsletterPostService;

    @Override
    @Transactional
    public Message register(final Long postId, final Long currentMemberId) {
        boolean exists = newsletterLikeService.isNewsletterExists(postId);
        if (!exists) {
            Newsletter newsletter = newsletterPostService.getNewsletter(postId);
            newsletterLikeService.registerLike(newsletter);
        }

        return Message.scrapPostSuccess(Newsletter.class, postId);
    }

    @Override
    @Transactional
    public Message cancel(final Long postId, final Long currentMemberId) {
        Newsletter newsletter = newsletterPostService.getNewsletter(postId);
        newsletterLikeService.cancelLike(newsletter);
        return Message.scrapPostCancel(Newsletter.class, postId);
    }
}
