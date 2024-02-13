package com.api.ttoklip.domain.mypage.noti.post.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record NoticePaging(List<NoticeSingleResponse> notices, Integer totalPage,
                           Long totalElements, Boolean isFirst, Boolean isLast) {
}
