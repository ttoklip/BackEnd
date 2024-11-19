package com.api.newsletter.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.newsletter.application.NewsletterLikeFacade;
import com.api.newsletter.application.NewsletterPostFacade;
import com.api.newsletter.application.NewsletterScrapFacade;
import com.api.newsletter.presentation.response.NewsCategoryPagingResponse;
import com.api.newsletter.presentation.response.NewsletterSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
    public class NewsletterPostController implements NewsletterPostControllerDocs {

    private static final int PAGE_SIZE = 10;

    private final NewsletterPostFacade newsletterPostFacade;
    private final NewsletterScrapFacade newsletterScrapFacade;
    private final NewsletterLikeFacade newsletterLikeFacade;

    @Override
    public TtoklipResponse<Message> register(NewsletterWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterPostFacade.register(request, currentMemberId));
    }

    @Override
    public TtoklipResponse<NewsletterSingleResponse> getSinglePost(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterPostFacade.getSinglePost(postId, currentMemberId));
    }

    @Override
    public TtoklipResponse<NewsCategoryPagingResponse> getPagingCategory(String category, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new TtoklipResponse<>(newsletterPostFacade.getPagingCategory(category, pageable));
    }

    @Override
    public TtoklipResponse<Message> report(Long postId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterPostFacade.report(postId, request, currentMemberId));
    }

    @Override
    public TtoklipResponse<Message> registerLike(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterLikeFacade.register(postId, currentMemberId));
    }

    @Override
    public TtoklipResponse<Message> cancelLike(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterLikeFacade.cancel(postId, currentMemberId));
    }

    @Override
    public TtoklipResponse<Message> registerScrap(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterScrapFacade.register(postId, currentMemberId));
    }

    @Override
    public TtoklipResponse<Message> cancelScrap(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterScrapFacade.cancel(postId, currentMemberId));
    }

    @Override
    public TtoklipResponse<Message> delete(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterPostFacade.delete(postId, currentMemberId));
    }
}
