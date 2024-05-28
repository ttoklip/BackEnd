package com.api.ttoklip.domain.mypage.main.service;



import com.api.ttoklip.domain.member.dto.response.TargetMemberProfile;
import com.api.ttoklip.domain.mypage.main.domain.*;
import com.api.ttoklip.domain.question.post.domain.Question;

import com.api.ttoklip.domain.search.response.*;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.mypage.main.dto.response.*;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartPaging;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;


@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberService memberService;
    private final MyQuestionRepository myQuestionRepository;
    private final MyCommunityRepostiory myCommunityRepostiory;
    private final MyHoneyTipRepository myHoneyTipRepository;
    private final MyNewsLetterRepository myNewsLetterRepository;
    private final MyCartRepository myCartRepository;

    public TargetMemberProfile getMyProfile() {
        return memberService.getTargetMemberProfile(getCurrentMember().getId());
    }

    public Message blockedUser() {
        return null;
    }

    public Message restricted() {
        return null;
    }

    public Message unblock(Long targetId) {
        /*Member currentMember = memberService.findByIdWithProfile(getCurrentMember().getId());
        List<Member> blockedUsers = currentMember.getBlockedUsers();

        for (Iterator<Member> iterator = blockedUsers.iterator(); iterator.hasNext(); ) {
            Member blockedUser = iterator.next();
            if (blockedUser.getId().equals(targetId)) {
                iterator.remove();
            }
        }*/
        return Message.activateUser();
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
        Page<Community> contentPaging = myCommunityRepostiory.getScrapContain(currentMember.getId(), pageable);
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
        Page<Question> contentPaging = myQuestionRepository.getContain(currentMember.getId(),pageable);
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
        Page<Community> contentPaging = myCommunityRepostiory.getContain(currentMember.getId(), pageable);
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
        Page<Cart> contentPaging = myCartRepository.getContain(currentMember.getId(),pageable);
        List<Cart> contents = contentPaging.getContent();
        List<UserCartSingleResponse> cartSingleData=contents.stream()
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
