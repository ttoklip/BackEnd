package com.api.ttoklip.domain.bulletin.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NoticePaging(List<NoticeSingleResponse> notices, Integer totalPage,
                           Long totalElements, Boolean isFirst, Boolean isLast) {
}
