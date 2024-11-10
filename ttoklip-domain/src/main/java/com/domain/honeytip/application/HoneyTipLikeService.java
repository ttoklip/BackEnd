package com.domain.honeytip.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipLike;
import com.domain.honeytip.domain.HoneyTipLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipLikeService {

    private final HoneyTipLikeRepository honeyTipLikeRepository;

    public boolean isHoneyTipLikeExists(final Long postId, final Long currentMemberId) {
        return honeyTipLikeRepository.existsByHoneyTipIdAndMemberId(postId, currentMemberId);
    }

    // 좋아요 생성
    @SendNotification
    public void register(final HoneyTip honeyTip, final Member member) {
        HoneyTipLike honeyTipLike = HoneyTipLike.of(honeyTip, member);
        honeyTipLikeRepository.save(honeyTipLike);
    }

    // 좋아요 취소
    public void cancel(final HoneyTip honeyTip, final Long memberId) {
        Long findHoneyTipId = honeyTip.getId();
        HoneyTipLike honeyTipLike = honeyTipLikeRepository.findByHoneyTipIdAndMemberId(findHoneyTipId, memberId)
                .orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByHoneyTipIdAndMemberId 결과가 존재하므로, 현재 사용자가 좋아요를 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        honeyTipLikeRepository.deleteById(honeyTipLike.getId());
    }

    public Long countHoneyTipLikes(final Long postId) {
        return honeyTipLikeRepository.countHoneyTipLikesByHoneyTipId(postId);
    }
}
