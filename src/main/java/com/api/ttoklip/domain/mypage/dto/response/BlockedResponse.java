package com.api.ttoklip.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BlockedResponse {
    private Long userId;
    private String userNickname;
    private String reason;
}
