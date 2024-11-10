package com.api.honeytip.application;

import com.api.common.vo.ActionFacade;
import com.api.global.success.Message;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.application.HoneyTipScrapService;
import com.domain.honeytip.domain.HoneyTip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipScrapFacade implements ActionFacade {

    private final HoneyTipScrapService honeyTipScrapService;
    private final HoneyTipPostService honeyTipPostService;
    private final MemberService memberService;

    @Override
    @Transactional
    public Message register(final Long postId, final Long currentMemberId) {
        boolean exists = honeyTipScrapService.isHoneyTipScrapExists(postId, currentMemberId);
        // 스크랩이 존재하지 않을 때만 생성
        if (!exists) {
            HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);
            Member currentMember = memberService.findById(currentMemberId);
            honeyTipScrapService.register(findHoneyTip, currentMember);
        }
        return Message.scrapPostSuccess(HoneyTip.class, postId);
    }

    @Override
    @Transactional
    public Message cancel(final Long postId, final Long currentMemberId) {
        HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);
        honeyTipScrapService.cancelScrap(findHoneyTip, currentMemberId);
        return Message.scrapPostCancel(HoneyTip.class, postId);
    }
}
