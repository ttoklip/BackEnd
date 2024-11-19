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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController implements MyPageControllerDocs {

    private static final int PAGE_SIZE = 10;

    private final MyPageFacade myPageFacade;
    private final ProfileFacade profileFacade;

    @Override
    public TtoklipResponse<TargetMemberProfile> getMyProfile() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.getMyProfile(currentMemberId));
    }

    @Override
    public TtoklipResponse<Message> edit(ProfileWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileFacade.edit(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<HoneyTipPaging> scrapHoneyTips(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapHoneyTips(currentMemberId, pageable));
    }

    @Override
    public TtoklipResponse<NewsletterPaging> scrapNewsletters(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapNewsletters(currentMemberId, pageable));
    }

    @Override
    public TtoklipResponse<CommunityPaging> scrapCommunity(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapCommunity(currentMemberId, pageable));
    }

    @Override
    public TtoklipResponse<HoneyTipPaging> myHoneyTip(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myHoneyTips(currentMemberId, pageable));
    }

    @Override
    public TtoklipResponse<QuestionPaging> myQuestion(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myQuestions(currentMemberId, pageable));
    }

    @Override
    public TtoklipResponse<CommunityPaging> myCommunity(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myCommunities(currentMemberId, pageable));
    }

    @Override
    public TtoklipResponse<CartPaging> participateDeals(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.participateDeals(currentMemberId, pageable));
    }
}
