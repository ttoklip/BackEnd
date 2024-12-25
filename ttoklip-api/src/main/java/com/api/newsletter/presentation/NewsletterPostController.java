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
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/newsletter/posts")
    public class NewsletterPostController implements NewsletterPostControllerDocs {

    private static final int PAGE_SIZE = 10;

    private final NewsletterPostFacade newsletterPostFacade;
    private final NewsletterScrapFacade newsletterScrapFacade;
    private final NewsletterLikeFacade newsletterLikeFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> register(
            final @Validated @ModelAttribute NewsletterWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                newsletterPostFacade.register(request, currentMemberId)
        );
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<NewsletterSingleResponse> getSinglePost(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                newsletterPostFacade.getSinglePost(postId, currentMemberId)
        );
    }

    @Override
    @GetMapping
    public TtoklipResponse<NewsCategoryPagingResponse> getPagingCategory(
            final @RequestParam String category,
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                newsletterPostFacade.getPagingCategory(category, pageable)
        );
    }

    @Override
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(
            final @PathVariable Long postId,
            final @RequestBody ReportWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                newsletterPostFacade.report(postId, request, currentMemberId)
        );
    }

    @Override
    @PostMapping("/like/{postId}")
    public TtoklipResponse<Message> registerLike(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                newsletterLikeFacade.register(postId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/like/{postId}")
    public TtoklipResponse<Message> cancelLike(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                newsletterLikeFacade.cancel(postId, currentMemberId)
        );
    }

    @Override
    @PostMapping("/scrap/{postId}")
    public TtoklipResponse<Message> registerScrap(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                newsletterScrapFacade.register(postId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/scrap/{postId}")
    public TtoklipResponse<Message> cancelScrap(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                newsletterScrapFacade.cancel(postId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/{postId}")
    public TtoklipResponse<Message> delete(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                newsletterPostFacade.delete(postId, currentMemberId)
        );
    }


}
