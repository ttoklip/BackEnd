package com.api.stranger.application;

import com.api.global.success.Message;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.search.presentation.response.CommonThumbnailResponse;
import com.api.stranger.presentation.StrangerResponse;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StrangerFacade {
    private final MemberService memberService;
    private final HoneyTipPostService honeyTipPostService;

    public StrangerResponse getStrangerProfile(final String nickname) {
        Member stranger = memberService.findByNickNameWithProfile(nickname);
        return StrangerResponse.of(stranger);
    }

    public HoneyTipPaging strangerHoneyTip(final Pageable pageable, final Long targetId) {
        Page<HoneyTip> contentPaging = honeyTipPostService.matchWriterPaging(targetId, pageable);
        List<HoneyTip> contents = contentPaging.getContent();
        List<CommonThumbnailResponse> honeyTipSingleData = contents.stream()
                .map(CommonThumbnailResponse::honeyTipFrom)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public Message participateDeals(final Pageable pageable, final Long targetId) {
        return null;
    }
}
