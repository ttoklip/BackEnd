package com.api.ttoklip.domain.honeytip.facade;

import com.api.ttoklip.domain.common.ActionFacade;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.service.HoneyTipLikeService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipLikeFacade implements ActionFacade {

    private final HoneyTipLikeService honeyTipLikeService;
    private final HoneyTipPostService honeyTipPostService;
    private final MemberService memberService;

    @Override
    @Transactional
    public Message register(final Long postId, final Long currentMemberId) {
        boolean exists = honeyTipLikeService.isHoneyTipLikeExists(postId, currentMemberId);
        // 좋아요가 존재하지 않을 때만 생성
        if (!exists) {
            HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);
            Member currentMember = memberService.findById(currentMemberId);
            honeyTipLikeService.register(findHoneyTip, currentMember);
        }
        return Message.likePostSuccess(HoneyTip.class, postId);
    }

    @Override
    @Transactional
    public Message cancel(final Long postId, final Long currentMemberId) {
        HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);
        honeyTipLikeService.cancel(findHoneyTip, currentMemberId);
        return Message.likePostCancel(HoneyTip.class, postId);
    }
}
