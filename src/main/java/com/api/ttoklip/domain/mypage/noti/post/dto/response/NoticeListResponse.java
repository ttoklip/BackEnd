package com.api.ttoklip.domain.mypage.noti.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class NoticeListResponse {
    private List<NoticeResponse> notices;
}
