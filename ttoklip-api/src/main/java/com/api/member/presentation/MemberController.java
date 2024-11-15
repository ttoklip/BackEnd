package com.api.member.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.member.application.MemberFacade;
import com.domain.member.application.response.MemberStreetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member API", description = "Member 정보 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberFacade memberFacade;

    @GetMapping("/street")
    @Operation(summary = "현재 사용자의 주소와 서울 거주 여부를 반환합니다.", description = "현재 사용자의 주소와 서울 거주 여부를 반환 API")
    public TtoklipResponse<MemberStreetResponse> getMemberStreet() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(memberFacade.getMemberStreet(currentMemberId));
    }
}
