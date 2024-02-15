package com.api.ttoklip.domain.honeytip.scrap.service;

import com.api.ttoklip.domain.honeytip.scrap.domain.HoneyTipScrap;
import com.api.ttoklip.domain.honeytip.scrap.repository.HoneyTipScrapRepository;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipCommonService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipScrapService {

    private final HoneyTipScrapRepository honeyTipScrapRepository;
    private final HoneyTipCommonService honeyTipCommonService;

    // 스크랩 생성
    public void registerScrap(final Long honeyTipId) {
        Long currentMemberId = getCurrentMember().getId();
        boolean exists = honeyTipScrapRepository.existsByHoneyTipIdAndMemberId(honeyTipId, currentMemberId);
        if (exists) {
            return; // 이미 스크랩이 존재하면 좋아요를 생성하지 않고 return
        }

        HoneyTip findHoneyTip = honeyTipCommonService.getHoneytip(honeyTipId);
        HoneyTipScrap honeyTipScrap = HoneyTipScrap.from(findHoneyTip);
        honeyTipScrapRepository.save(honeyTipScrap);
    }

    // 스크랩 취소
    public void cancelScrap(final Long honeyTipId) {
        // HoneyTipId (게시글 ID)
        HoneyTip findHoneyTip = honeyTipCommonService.getHoneytip(honeyTipId);
        Long findHoneyTipId = findHoneyTip.getId();
        Long currentMemberId = getCurrentMember().getId();

        HoneyTipScrap honeyTipScrap = honeyTipScrapRepository.findByHoneyTipIdAndMemberId(findHoneyTipId, currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType.SCRAP_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByHoneyTipIdAndMemberId 결과가 존재하므로, 현재 사용자가 스크랩을 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        honeyTipScrapRepository.deleteById(honeyTipScrap.getId());
    }

    public Long countHoneyTipScraps(final Long honeyTipId) {
        return honeyTipScrapRepository.countHoneyTipScrapsByHoneyTipId(honeyTipId);
    }
}
