package com.api.member.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.domain.member.application.response.MemberStreetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Member API", description = "Member 정보 API")
@RequestMapping("/api/v1/member")
public interface MemberControllerDocs {

    @Operation(summary = "현재 사용자의 주소와 서울 거주 여부를 반환", description = "현재 사용자의 주소와 서울 거주 여부를 반환하는 API입니다.")
    @GetMapping("/street")
    TtoklipResponse<MemberStreetResponse> getMemberStreet();
}
