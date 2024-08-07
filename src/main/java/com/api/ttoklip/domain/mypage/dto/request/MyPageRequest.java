package com.api.ttoklip.domain.mypage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@AllArgsConstructor
@Jacksonized
@Builder
public class MyPageRequest {
    private String userNickName;
}
