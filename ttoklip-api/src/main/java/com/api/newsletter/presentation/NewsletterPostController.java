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
import org.springframework.web.bind.annotation.*;

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
    public TtoklipResponse<Message> register(@Validated @ModelAttribute NewsletterWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterPostFacade.register(request, currentMemberId));
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<NewsletterSingleResponse> getSinglePost(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterPostFacade.getSinglePost(postId, currentMemberId));
    }

    @Override
    @GetMapping
    public TtoklipResponse<NewsCategoryPagingResponse> getPagingCategory(@RequestParam String category,
                                                                         @RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new TtoklipResponse<>(newsletterPostFacade.getPagingCategory(category, pageable));
    }

    @Override
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(@PathVariable Long postId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterPostFacade.report(postId, request, currentMemberId));
    }

    @Override
    @PostMapping("/like/{postId}")
    public TtoklipResponse<Message> registerLike(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterLikeFacade.register(postId, currentMemberId));
    }

    @Override
    @DeleteMapping("/like/{postId}")
    public TtoklipResponse<Message> cancelLike(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterLikeFacade.cancel(postId, currentMemberId));
    }

    @Override
    @PostMapping("/scrap/{postId}")
    public TtoklipResponse<Message> registerScrap(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterScrapFacade.register(postId, currentMemberId));
    }

    @Override
    @DeleteMapping("/scrap/{postId}")
    public TtoklipResponse<Message> cancelScrap(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterScrapFacade.cancel(postId, currentMemberId));
    }

    @Override
    @DeleteMapping("/{postId}")
    public TtoklipResponse<Message> delete(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(newsletterPostFacade.delete(postId, currentMemberId));
    }


}
