package com.api.ttoklip.domain.honeytip.facade;

import com.api.ttoklip.domain.common.ActionFacade;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipScrapService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HoneyTipScrapFacade implements ActionFacade {

    private final HoneyTipScrapService honeyTipScrapService;
    private final HoneyTipPostService honeyTipPostService;
    private final MemberService memberService;

    @Override
    @Transactional
    public Message register(final Long postId, final Long currentMemberId) {
        Member currentMember = memberService.findById(currentMemberId);
        boolean exists = honeyTipScrapService.isHoneyTipScrapExists(postId, currentMember.getId());
        // 스크랩이 존재하지 않을 때만 생성
        if (!exists) {
            HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);
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
