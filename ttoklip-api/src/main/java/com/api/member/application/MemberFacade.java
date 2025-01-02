package com.api.member.application;

import com.api.global.support.response.Message;
import com.domain.member.application.response.MemberStreetResponse;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFacade {

    private final MemberService memberService;

    public MemberStreetResponse getMemberStreet(final Long currentMemberId) {
        Member currentMember = memberService.getById(currentMemberId);
        return memberService.getMemberStreet(currentMember);
    }

    public Message banMember(final Long memberId) {
        memberService.ban(memberId);
        return Message.banMember(memberId);
    }
}
