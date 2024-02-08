package com.api.ttoklip.domain.town.community.like.service;

import com.api.ttoklip.domain.Member;
import com.api.ttoklip.domain.MemberRepository;
import com.api.ttoklip.domain.town.community.like.repository.LikeRepository;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.domain.town.community.like.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Message pushLikeButton(Long memberId, Long communityId) {
        Member member = memberRepository.findById(memberId).
                orElseThrow(() -> new ApiException(ErrorType.MEMBER_NOT_FOUND));

        Community community = communityRepository.findById(communityId).
                orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUND));

        Like like = likeRepository.findByMemberAndCommunity(member, community);
        if(like == null) {
            Like newLike = new Like(member, community);
            likeRepository.save(newLike);
            return Message.createLikeSuccess(Community.class, communityId);
        } else {
            likeRepository.delete(like);
            return Message.deleteLikeSuccess(Community.class, communityId);
        }
    }
}
