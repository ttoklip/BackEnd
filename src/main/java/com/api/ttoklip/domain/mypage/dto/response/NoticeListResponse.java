package com.api.ttoklip.domain.mypage.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class NoticeListResponse {
    private List<NoticeResponse> notices;
}
