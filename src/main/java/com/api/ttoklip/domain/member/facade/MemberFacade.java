package com.api.ttoklip.domain.member.facade;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.dto.response.MemberStreetResponse;
import com.api.ttoklip.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFacade {

    private final MemberService memberService;

    public MemberStreetResponse getMemberStreet(final Long currentMemberId) {
        Member currentMember = memberService.findById(currentMemberId);
        return memberService.getMemberStreet(currentMember);
    }
}
