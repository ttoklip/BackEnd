package com.api.ttoklip.domain.bulletin.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NoticeListResponse {
    private List<NoticeResponse> notices;
}
