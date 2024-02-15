package com.api.ttoklip.domain.town.community.like.service;

import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import com.api.ttoklip.domain.town.community.like.repository.CommunityLikeRepository;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.service.CommunityCommonService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityLikeService {

    private final CommunityLikeRepository communityLikeRepository;
    private final CommunityCommonService communityCommonService;

    // 좋아요 생성
    public void register(final Long communityId) {
//        boolean exists = communityLikeRepository.existsById(communityId);
        Long currentMemberId = getCurrentMember().getId();
        boolean exists = communityLikeRepository.existsByCommunityIdAndMemberId(communityId, currentMemberId);
        if (exists) {
            return; // 이미 좋아요가 존재하면 좋아요를 생성하지 않고 return
        }

        Community findCommunity = communityCommonService.getCommunity(communityId);
        CommunityLike communityLike = CommunityLike.from(findCommunity);
        communityLikeRepository.save(communityLike);
    }

    // 좋아요 취소
    public void cancel(final Long communityId) {
        // HoneyTipId (게시글 ID)
        Community findCommunity = communityCommonService.getCommunity(communityId);
        Long findCommunityId = findCommunity.getId();
        Long currentMemberId = getCurrentMember().getId();

        CommunityLike communityLike = communityLikeRepository.findByCommunityIdAndMemberId(findCommunityId, currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByHoneyTipIdAndMemberId 결과가 존재하므로, 현재 사용자가 좋아요를 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        communityLikeRepository.deleteById(communityLike.getId());
    }

    public Long countCommunityLikes(final Long communityId) {
        return communityLikeRepository.countCommunityLikesByCommunityId(communityId);
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
