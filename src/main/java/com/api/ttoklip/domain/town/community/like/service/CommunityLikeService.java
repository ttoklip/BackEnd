package com.api.ttoklip.domain.town.community.like.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import com.api.ttoklip.domain.town.community.like.repository.CommunityLikeRepository;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.domain.town.community.post.service.CommunityCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityLikeService {

    private final CommunityLikeRepository communityLikeRepository;
    private final CommunityCommonService communityCommonService;
//    private final MemberRepository memberRepository;

    public static Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    public void pushLikeButton(final Long communityId) {
        // 좋아요 생성
        Community findCommunity = CommunityLikeService.getCommunity(communityId);
        Member currentMember = getCurrentMember();

        CommunityLike communityLike = CommunityLike.of(currentMember, findCommunity);
        communityLikeRepository.save(communityLike);
    }


//    @Transactional
//    public Message pushLikeButton(Long memberId, Long communityId) {
//        Member member = memberRepository.findById(memberId).
//                orElseThrow(() -> new ApiException(ErrorType.MEMBER_NOT_FOUND));
//
//        Community community = communityRepository.findById(communityId).
//                orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUND));
//
//        CommunityLike communityLike = communityLikeRepository.findByMemberAndCommunity(member, community);
//        if(communityLike == null) {
//            CommunityLike newCommunityLike = new CommunityLike(member, community);
//            communityLikeRepository.save(newCommunityLike);
//            return Message.createLikeSuccess(Community.class, communityId);
//        } else {
//            communityLikeRepository.delete(communityLike);
//            return Message.deleteLikeSuccess(Community.class, communityId);
//        }
//    }
}
