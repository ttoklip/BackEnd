package com.api.ttoklip.domain.mypage.noti.post.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NoticePaging(List<NoticeSingleResponse> notices, Integer totalPage,
                           Long totalElements, Boolean isFirst, Boolean isLast) {
}
