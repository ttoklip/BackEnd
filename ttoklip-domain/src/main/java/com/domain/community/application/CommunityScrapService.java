package com.domain.community.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityScrap;
import com.domain.community.domain.CommunityScrapRepository;
import com.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityScrapService {

    private final CommunityScrapRepository communityScrapRepository;

    // 스크랩 생성
    public void registerScrap(final Community community, final Member member) {
        boolean exists = communityScrapRepository.existsByCommunityIdAndMemberId(community.getId(), member.getId());
        if (exists) {
            return;
        }

        CommunityScrap communityScrap = CommunityScrap.from(community, member);
        communityScrapRepository.save(communityScrap);
    }

    // 스크랩 취소
    public void cancelScrap(final Long communityId, final Long memberId) {
        CommunityScrap communityScrap = communityScrapRepository.findByCommunityIdAndMemberId(communityId, memberId)
                .orElseThrow(() -> new ApiException(ErrorType.SCRAP_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByHoneyTipIdAndMemberId 결과가 존재하므로, 현재 사용자가 좋아요를 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        communityScrapRepository.deleteById(communityScrap.getId());
    }

    public Long countCommunityScraps(final Long communityId) {
        return communityScrapRepository.countCommunityScrapsByCommunityId(communityId);
    }

    public boolean existsByCommunityIdAndMemberId(final Long postId, final Long currentMemberId) {
        return communityScrapRepository.existsByCommunityIdAndMemberId(postId, currentMemberId);
    }

}
