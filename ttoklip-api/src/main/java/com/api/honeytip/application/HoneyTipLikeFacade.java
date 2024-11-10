package com.api.honeytip.application;

import com.api.common.vo.ActionFacade;
import com.api.global.success.Message;
import com.domain.honeytip.application.HoneyTipLikeService;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.domain.HoneyTip;
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
