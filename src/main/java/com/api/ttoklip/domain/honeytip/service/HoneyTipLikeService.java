package com.api.ttoklip.domain.honeytip.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.aop.notification.annotation.SendNotification;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipLike;
import com.api.ttoklip.domain.honeytip.repository.like.HoneyTipLikeRepository;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipLikeService {

    private final HoneyTipLikeRepository honeyTipLikeRepository;

    public boolean isHoneyTipLikeExists(final Long honeyTipId) {
        Long currentMemberId = getCurrentMember().getId();
        return honeyTipLikeRepository.existsByHoneyTipIdAndMemberId(honeyTipId, currentMemberId);
    }

    // 좋아요 생성
    @SendNotification
    public void register(final HoneyTip honeyTip) {
        HoneyTipLike honeyTipLike = HoneyTipLike.from(honeyTip);
        honeyTipLikeRepository.save(honeyTipLike);
    }

    // 좋아요 취소
    public void cancel(final HoneyTip honeyTip) {
        Long findHoneyTipId = honeyTip.getId();
        Long currentMemberId = getCurrentMember().getId();

        HoneyTipLike honeyTipLike = honeyTipLikeRepository.findByHoneyTipIdAndMemberId(findHoneyTipId, currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByHoneyTipIdAndMemberId 결과가 존재하므로, 현재 사용자가 좋아요를 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        honeyTipLikeRepository.deleteById(honeyTipLike.getId());
    }

    public Long countHoneyTipLikes(final Long honeyTipId) {
        return honeyTipLikeRepository.countHoneyTipLikesByHoneyTipId(honeyTipId);
    }
}
