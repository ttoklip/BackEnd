package com.api.mypage.application;


import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.question.presentation.dto.response.QuestionPaging;
import com.api.question.presentation.dto.response.vo.QuestionThumbnailResponse;
import com.api.search.presentation.response.CommonThumbnailResponse;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.search.presentation.response.NewsletterPaging;
import com.api.town.application.TownThumbnailResponse;
import com.domain.cart.application.CartMemberService;
import com.domain.cart.application.CartThumbnailResponse;
import com.domain.cart.domain.Cart;
import com.domain.community.application.CommunityPostService;
import com.domain.community.application.CommunityScrapService;
import com.domain.community.domain.Community;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.application.HoneyTipScrapService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.member.application.MemberService;
import com.domain.newsletter.application.NewsletterScrapService;
import com.domain.newsletter.domain.Newsletter;
import com.domain.profile.application.response.TargetMemberProfile;
import com.domain.question.application.QuestionPostService;
import com.domain.question.domain.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyPageFacade {

    private final CartMemberService cartMemberService;
    private final HoneyTipPostService honeyTipPostService;
    private final HoneyTipScrapService honeyTipScrapService;
    private final NewsletterScrapService newsletterScrapService;
    private final CommunityPostService communityPostService;
    private final CommunityScrapService communityScrapService;
    private final QuestionPostService questionPostService;
    private final MemberService memberService;

    public TargetMemberProfile getMyProfile(final Long memberId) {
        return memberService.getTargetMemberProfile(memberId);
    }

    public HoneyTipPaging scrapHoneyTips(final Long memberId, final Pageable pageable) {
        Page<HoneyTip> scrapPaging = honeyTipScrapService.getScrapPaging(memberId, pageable);
        List<HoneyTip> contents = scrapPaging.getContent();
        List<CommonThumbnailResponse> honeyTipSingleData = contents.stream()
                .map(CommonThumbnailResponse::honeyTipFrom)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(scrapPaging.isFirst())
                .isLast(scrapPaging.isLast())
                .totalElements(scrapPaging.getTotalElements())
                .totalPage(scrapPaging.getTotalPages())
                .build();

    }

    public NewsletterPaging scrapNewsletters(final Long memberId, final Pageable pageable) {
        Page<Newsletter> scrapPaging = newsletterScrapService.getScrapPaging(memberId, pageable);
        List<Newsletter> contents = scrapPaging.getContent();
        List<CommonThumbnailResponse> newsletterSingleData = contents.stream()
                .map(CommonThumbnailResponse::newsletterFrom)
                .toList();

        return NewsletterPaging.builder()
                .newsletters(newsletterSingleData)
                .isFirst(scrapPaging.isFirst())
                .isLast(scrapPaging.isLast())
                .totalElements(scrapPaging.getTotalElements())
                .totalPage(scrapPaging.getTotalPages())
                .build();
    }

    public CommunityPaging scrapCommunity(final Long memberId, final Pageable pageable) {
        Page<Community> scrapPaging = communityScrapService.getScrapPaging(memberId, pageable);
        List<Community> contents = scrapPaging.getContent();
        List<TownThumbnailResponse> communitySingleData = contents.stream()
                .map(TownThumbnailResponse::from)
                .toList();

        return CommunityPaging.builder()
                .communities(communitySingleData)
                .isFirst(scrapPaging.isFirst())
                .isLast(scrapPaging.isLast())
                .totalElements(scrapPaging.getTotalElements())
                .totalPage(scrapPaging.getTotalPages())
                .build();
    }

    public CommunityPaging myCommunities(final Long memberId, final Pageable pageable) {
        Page<Community> matchWriterPaging = communityPostService.getMatchWriterPaging(memberId, pageable);
        List<Community> contents = matchWriterPaging.getContent();
        List<TownThumbnailResponse> communitySingleData = contents.stream()
                .map(TownThumbnailResponse::from)
                .toList();

        return CommunityPaging.builder()
                .communities(communitySingleData)
                .isFirst(matchWriterPaging.isFirst())
                .isLast(matchWriterPaging.isLast())
                .totalElements(matchWriterPaging.getTotalElements())
                .totalPage(matchWriterPaging.getTotalPages())
                .build();
    }

    public QuestionPaging myQuestions(final Long memberId, final Pageable pageable) {
        Page<Question> matchWriterPaging = questionPostService.getMatchWriterPaging(memberId, pageable);
        List<Question> contents = matchWriterPaging.getContent();
        List<QuestionThumbnailResponse> questionSingleData = contents.stream()
                .map(QuestionThumbnailResponse::from)
                .toList();

        return QuestionPaging.builder()
                .questions(questionSingleData)
                .isFirst(matchWriterPaging.isFirst())
                .isLast(matchWriterPaging.isLast())
                .totalElements(matchWriterPaging.getTotalElements())
                .totalPage(matchWriterPaging.getTotalPages())
                .build();

    }

    public HoneyTipPaging myHoneyTips(final Long memberId, final Pageable pageable) {
        Page<HoneyTip> contentPaging = honeyTipPostService.matchWriterPaging(memberId, pageable);
        List<HoneyTip> contents = contentPaging.getContent();
        List<CommonThumbnailResponse> honeyTipSingleData = contents.stream()
                .map(CommonThumbnailResponse::honeyTipFrom)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CartPaging participateDeals(final Long memberId, final Pageable pageable) {
        Page<Cart> contentPaging = cartMemberService.findParticipatingCartsByUserId(memberId, pageable);
        List<Cart> contents = contentPaging.getContent();
        List<CartThumbnailResponse> cartSingleData = contents.stream()
                .map(CartThumbnailResponse::from)
                .toList();

        return CartPaging.builder()
                .carts(cartSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }
}
