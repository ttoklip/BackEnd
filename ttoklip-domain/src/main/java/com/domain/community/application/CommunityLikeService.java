package com.domain.community.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityLike;
import com.domain.community.domain.CommunityLikeRepository;
import com.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityLikeService {

    private final CommunityLikeRepository communityLikeRepository;

    public void register(final Community community, final Member member) {
        boolean exists = communityLikeRepository.existsByCommunityIdAndMemberId(community.getId(), member.getId());
        if (exists) {
            return;
        }

        CommunityLike communityLike = CommunityLike.from(community, member);
        communityLikeRepository.save(communityLike);
    }

    public void cancel(final Long communityId, final Long memberId) {
        CommunityLike communityLike = communityLikeRepository.findByCommunityIdAndMemberId(communityId, memberId)
                .orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByHoneyTipIdAndMemberId 결과가 존재하므로, 현재 사용자가 좋아요를 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        communityLikeRepository.deleteById(communityLike.getId());
    }

    public Long countCommunityLikes(final Long communityId) {
        return communityLikeRepository.countCommunityLikesByCommunityId(communityId);
    }

    public boolean existsByCommunityIdAndMemberId(final Long postId, final Long currentMemberId) {
        return communityLikeRepository.existsByCommunityIdAndMemberId(postId, currentMemberId);
    }

}
