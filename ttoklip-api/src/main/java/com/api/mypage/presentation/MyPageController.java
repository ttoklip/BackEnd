package com.api.mypage.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.mypage.application.MyPageFacade;
import com.api.profile.application.ProfileFacade;
import com.api.profile.presentation.ProfileWebCreate;
import com.api.question.presentation.dto.response.QuestionPaging;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.search.presentation.response.NewsletterPaging;
import com.domain.profile.application.response.TargetMemberProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/my-page")
public class MyPageController implements MyPageControllerDocs {

    private static final int PAGE_SIZE = 10;

    private final MyPageFacade myPageFacade;
    private final ProfileFacade profileFacade;

    @Override
    @GetMapping
    public TtoklipResponse<TargetMemberProfile> getMyProfile() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                myPageFacade.getMyProfile(currentMemberId)
        );
    }

    @Override
    @PatchMapping("/edit")
    public TtoklipResponse<Message> edit(
            final @ModelAttribute @Validated ProfileWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                profileFacade.edit(request, currentMemberId)
        );
    }

    @Override
    @GetMapping("/scrap-post/honeytip")
    public TtoklipResponse<HoneyTipPaging> scrapHoneyTips(
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                myPageFacade.scrapHoneyTips(currentMemberId, pageable)
        );
    }

    @Override
    @GetMapping("/scrap-post/newsletter")
    public TtoklipResponse<NewsletterPaging> scrapNewsletters(
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                myPageFacade.scrapNewsletters(currentMemberId, pageable)
        );
    }

    @Override
    @GetMapping("/scrap-post/community")
    public TtoklipResponse<CommunityPaging> scrapCommunity(
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                myPageFacade.scrapCommunity(currentMemberId, pageable)
        );
    }

    @Override
    @GetMapping("/honeytip")
    public TtoklipResponse<HoneyTipPaging> myHoneyTip(
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                myPageFacade.myHoneyTips(currentMemberId, pageable)
        );
    }

    @Override
    @GetMapping("/question")
    public TtoklipResponse<QuestionPaging> myQuestion(
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                myPageFacade.myQuestions(currentMemberId, pageable)
        );
    }

    @Override
    @GetMapping("/community")
    public TtoklipResponse<CommunityPaging> myCommunity(
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                myPageFacade.myCommunities(currentMemberId, pageable)
        );
    }

    @Override
    @GetMapping("/participate-deals")
    public TtoklipResponse<CartPaging> participateDeals(
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                myPageFacade.participateDeals(currentMemberId, pageable)
        );
    }
}
