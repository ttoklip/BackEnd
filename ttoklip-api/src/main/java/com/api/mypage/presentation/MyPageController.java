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
import org.springframework.web.bind.annotation.*;

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
        return new TtoklipResponse<>(myPageFacade.getMyProfile(currentMemberId));
    }

    @Override
    @PatchMapping("/edit")
    public TtoklipResponse<Message> edit(@ModelAttribute @Validated ProfileWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileFacade.edit(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/scrap-post/honeytip")
    public TtoklipResponse<HoneyTipPaging> scrapHoneyTips(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapHoneyTips(currentMemberId, pageable));
    }

    @Override
    @GetMapping("/scrap-post/newsletter")
    public TtoklipResponse<NewsletterPaging> scrapNewsletters(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapNewsletters(currentMemberId, pageable));
    }

    @Override
    @GetMapping("/scrap-post/community")
    public TtoklipResponse<CommunityPaging> scrapCommunity(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapCommunity(currentMemberId, pageable));
    }

    @Override
    @GetMapping("/honeytip")
    public TtoklipResponse<HoneyTipPaging> myHoneyTip(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myHoneyTips(currentMemberId, pageable));
    }

    @Override
    @GetMapping("/question")
    public TtoklipResponse<QuestionPaging> myQuestion(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myQuestions(currentMemberId, pageable));
    }

    @Override
    @GetMapping("/community")
    public TtoklipResponse<CommunityPaging> myCommunity(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myCommunities(currentMemberId, pageable));
    }

    @Override
    @GetMapping("/participate-deals")
    public TtoklipResponse<CartPaging> participateDeals(@RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.participateDeals(currentMemberId, pageable));
    }
}
