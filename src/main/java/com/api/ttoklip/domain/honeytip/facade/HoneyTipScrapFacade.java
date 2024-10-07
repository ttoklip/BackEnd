package com.api.ttoklip.domain.honeytip.facade;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipScrapService;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HoneyTipScrapFacade {

    private final HoneyTipScrapService honeyTipScrapService;
    private final HoneyTipPostService honeyTipPostService;

    @Transactional
    public Message registerScrap(Long postId) {
        boolean exists = honeyTipScrapService.isHoneyTipScrapExists(postId);
        // 스크랩이 존재하지 않을 때만 생성
        if (!exists) {
            HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);
            honeyTipScrapService.register(findHoneyTip);
        }
        return Message.scrapPostSuccess(HoneyTip.class, postId);
    }

    @Transactional
    public Message cancelScrap(Long postId) {
        HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);
        honeyTipScrapService.cancelScrap(findHoneyTip);
        return Message.scrapPostCancel(HoneyTip.class, postId);
    }
}
