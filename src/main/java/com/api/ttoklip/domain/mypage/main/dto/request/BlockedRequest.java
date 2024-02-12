package com.api.ttoklip.domain.mypage.main.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@AllArgsConstructor
@Jacksonized
public class BlockedRequest {
    private Long userId;
}
