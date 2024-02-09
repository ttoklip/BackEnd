package com.api.ttoklip.domain.honeytip.like.service;

import com.api.ttoklip.domain.honeytip.like.domain.HoneyTipLike;
import com.api.ttoklip.domain.honeytip.like.repository.HoneyTipLikeRepository;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipCommonService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipLikeService {

    private final HoneyTipLikeRepository honeyTipLikeRepository;
    private final HoneyTipCommonService honeyTipCommonService;

    public static Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    public void register(final Long honeyTipId) {
        // 좋아요 생성
        HoneyTip findHoneyTip = honeyTipCommonService.getHoneytip(honeyTipId);
        Member currentMember = getCurrentMember();

        HoneyTipLike honeyTipLike = HoneyTipLike.of(currentMember, findHoneyTip);
        honeyTipLikeRepository.save(honeyTipLike);
    }
}
