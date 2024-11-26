package com.api.member.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.domain.member.application.response.MemberStreetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Member API", description = "Member 정보 API")
public interface MemberControllerDocs {

    @Operation(summary = "현재 사용자의 주소와 서울 거주 여부를 반환", description = "현재 사용자의 주소와 서울 거주 여부를 반환하는 API입니다.")
    TtoklipResponse<MemberStreetResponse> getMemberStreet();
}
