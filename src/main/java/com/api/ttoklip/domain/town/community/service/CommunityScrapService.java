package com.api.ttoklip.domain.town.community.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.domain.town.community.domain.CommunityScrap;
import com.api.ttoklip.domain.town.community.repository.CommunityScrapRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityScrapService {

    private final CommunityScrapRepository communityScrapRepository;
    private final CommunityCommonService communityCommonService;

    // 스크랩 생성
    public void registerScrap(final Long communityId) {
        // boolean exists = communityScrapRepository.existsById(communityId);
        Long currentMemberId = getCurrentMember().getId();
        boolean exists = communityScrapRepository.existsByCommunityIdAndMemberId(communityId, currentMemberId);
        if (exists) {
            return; // 이미 스크랩이 존재하면 좋아요를 생성하지 않고 return
        }

        Community findCommunity = communityCommonService.getCommunity(communityId);
        CommunityScrap communityScrap = CommunityScrap.from(findCommunity);
        communityScrapRepository.save(communityScrap);
    }

    // 스크랩 취소
    public void cancelScrap(final Long communityId) {
        // CommunityId (게시글 ID)
        Community findCommunity = communityCommonService.getCommunity(communityId);
        Long findCommunityId = findCommunity.getId();
        Long currentMemberId = getCurrentMember().getId();

        CommunityScrap communityScrap = communityScrapRepository.findByCommunityIdAndMemberId(findCommunityId,
                        currentMemberId)
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
