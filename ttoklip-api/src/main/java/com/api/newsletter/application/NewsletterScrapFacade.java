package com.api.newsletter.application;

import com.api.common.vo.ActionFacade;
import com.api.global.support.response.Message;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.newsletter.application.NewsletterPostService;
import com.domain.newsletter.application.NewsletterScrapService;
import com.domain.newsletter.domain.Newsletter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterScrapFacade implements ActionFacade {

    private final NewsletterScrapService newsletterScrapService;
    private final NewsletterPostService newsletterPostService;
    private final MemberService memberService;

    @Override
    @Transactional
    public Message register(final Long postId, final Long memberId) {
        boolean exists = newsletterScrapService.isNewsletterExists(postId, memberId);
        if (!exists) {
            Newsletter newsletter = newsletterPostService.getNewsletter(postId);
            Member member = memberService.getById(memberId);
            newsletterScrapService.register(newsletter, member);
        }

        return Message.scrapPostSuccess(Newsletter.class, postId);
    }

    @Override
    @Transactional
    public Message cancel(final Long postId, final Long memberId) {
        Newsletter newsletter = newsletterPostService.getNewsletter(postId);
        newsletterScrapService.cancelScrap(newsletter, memberId);
        return Message.scrapPostCancel(Newsletter.class, postId);
    }

}
