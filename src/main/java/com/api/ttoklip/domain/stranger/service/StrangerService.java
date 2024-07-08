package com.api.ttoklip.domain.stranger.service;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.search.response.SingleResponse;
import com.api.ttoklip.domain.stranger.dto.response.StrangerResponse;
import com.api.ttoklip.domain.stranger.repository.StrangerHoneyTipRepository;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StrangerService {
    private final MemberService memberService;
    private final StrangerHoneyTipRepository strangerHoneyTipRepository;

    public StrangerResponse getStrangerProfile(final String nickname) {
        Member stranger = memberService.findByNickNameWithProfile(nickname);
        StrangerResponse strangerResponse = StrangerResponse.of(stranger);
        return strangerResponse;
    }

    public HoneyTipPaging strangerHoneyTip(final Pageable pageable, final Long targetId) {
        Member stranger = memberService.findByIdWithProfile(targetId);
        Page<HoneyTip> contentPaging = strangerHoneyTipRepository.getContain(targetId, pageable);
        // List<Entity>
        List<HoneyTip> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<SingleResponse> honeyTipSingleData = contents.stream()
                .map(SingleResponse::honeyTipFrom)
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
