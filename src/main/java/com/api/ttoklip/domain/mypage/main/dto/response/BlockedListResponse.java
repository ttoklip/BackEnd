package com.api.ttoklip.domain.mypage.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BlockedListResponse {
    private List<BlockedResponse> blockedResponses;
}
