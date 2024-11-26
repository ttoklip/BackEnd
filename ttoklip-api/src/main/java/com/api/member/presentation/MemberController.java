package com.api.member.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.member.application.MemberFacade;
import com.domain.member.application.response.MemberStreetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController implements MemberControllerDocs {

    private final MemberFacade memberFacade;

    @Override
    @GetMapping("/street")
    public TtoklipResponse<MemberStreetResponse> getMemberStreet() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(memberFacade.getMemberStreet(currentMemberId));
    }
}
