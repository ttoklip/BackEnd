package com.api.ttoklip.domain.mypage.service;


import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.profile.TargetMemberProfile;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.mypage.domain.MyCartRepository;
import com.api.ttoklip.domain.mypage.domain.MyCommunityPagingRepository;
import com.api.ttoklip.domain.mypage.domain.MyHoneyTipRepository;
import com.api.ttoklip.domain.mypage.domain.MyNewsLetterRepository;
import com.api.ttoklip.domain.mypage.domain.MyQuestionRepository;
import com.api.ttoklip.domain.mypage.dto.response.QuestionPaging;
import com.api.ttoklip.domain.mypage.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.mypage.dto.response.UserSingleResponse;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.search.response.CartPaging;
import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.CommunitySingleResponse;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.search.response.NewsletterPaging;
import com.api.ttoklip.domain.search.response.SingleResponse;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberService memberService;
    private final MyQuestionRepository myQuestionRepository;
    private final MyCommunityPagingRepository myCommunityPagingRepository;
    private final MyHoneyTipRepository myHoneyTipRepository;
    private final MyNewsLetterRepository myNewsLetterRepository;
    private final MyCartRepository myCartRepository;

    public TargetMemberProfile getMyProfile() {
        return memberService.getTargetMemberProfile(getCurrentMember().getId());
    }

    public HoneyTipPaging scrapHoneyTips(final Pageable pageable) {

        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        Page<HoneyTip> contentPaging = myHoneyTipRepository.getScrapContain(currentMember.getId(), pageable);

        // List<Entity>
        List<HoneyTip> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<SingleResponse> honeyTipSingleData = contents.stream()
                .map(SingleResponse::honeyTipFrom)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();

    }

    public NewsletterPaging scrapNewsletters(final Pageable pageable) {
        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        Page<Newsletter> contentPaging = myNewsLetterRepository.getScrapContain(currentMember.getId(), pageable);
        // List<Entity>
        List<Newsletter> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<SingleResponse> newsletterSingleData = contents.stream()
                .map(SingleResponse::newsletterFrom)
                .toList();
        return NewsletterPaging.builder()
                .newsletters(newsletterSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CommunityPaging scrapCommunity(final Pageable pageable) {

        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        Page<Community> contentPaging = myCommunityPagingRepository.getScrapContain(currentMember.getId(), pageable);
        // List<Entity>
        List<Community> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<CommunitySingleResponse> communitySingleData = contents.stream()
                .map(CommunitySingleResponse::from)
                .toList();

        return CommunityPaging.builder()
                .communities(communitySingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public QuestionPaging myQuestions(final Pageable pageable) {
        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        Page<Question> contentPaging = myQuestionRepository.getContain(currentMember.getId(), pageable);
        // List<Entity>
        List<Question> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<UserSingleResponse> questionSingleData = contents.stream()
                .map(UserSingleResponse::questionFrom)
                .toList();

        return QuestionPaging.builder()
                .questions(questionSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();

    }
    /*public Message myQuestions(final Pageable pageable) {
        return null;
    }*/

    public CommunityPaging myCommunities(final Pageable pageable) {
        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        Page<Community> contentPaging = myCommunityPagingRepository.getContain(currentMember.getId(), pageable);
        // List<Entity>
        List<Community> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<CommunitySingleResponse> communitySingleData = contents.stream()
                .map(CommunitySingleResponse::from)
                .toList();

        return CommunityPaging.builder()
                .communities(communitySingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public HoneyTipPaging myHoneyTips(final Pageable pageable) {
        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        Page<HoneyTip> contentPaging = myHoneyTipRepository.getContain(currentMember.getId(), pageable);
        // List<Entity>
        List<HoneyTip> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<SingleResponse> honeyTipSingleData = contents.stream()
                .map(SingleResponse::honeyTipFrom)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CartPaging participateDeals(final Pageable pageable) {
        Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        Page<Cart> contentPaging = myCartRepository.getContain(currentMember.getId(), pageable);
        List<Cart> contents = contentPaging.getContent();
        List<UserCartSingleResponse> cartSingleData = contents.stream()
                .map(UserCartSingleResponse::cartFrom)
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
