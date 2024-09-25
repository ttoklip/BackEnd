package com.api.ttoklip.domain.member.controller;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.member.dto.response.MemberStreetResponse;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Profile API", description = "Member 정보 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/street")
    public SuccessResponse<MemberStreetResponse> getMemberStreet() {
        return new SuccessResponse<>(memberService.getMemberStreet(getCurrentMember()));
    }
}
