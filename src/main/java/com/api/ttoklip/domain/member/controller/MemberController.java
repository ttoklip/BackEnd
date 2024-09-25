package com.api.ttoklip.domain.member.controller;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.member.dto.response.MemberStreetResponse;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.success.SuccessResponse;
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

    private final MemberService memberService;

    @GetMapping("/street")
    @Operation(summary = "현재 사용자의 주소와 서울 거주 여부를 반환합니다.", description = "현재 사용자의 주소와 서울 거주 여부를 반환 API")
    public SuccessResponse<MemberStreetResponse> getMemberStreet() {
        return new SuccessResponse<>(memberService.getMemberStreet(getCurrentMember()));
    }
}
