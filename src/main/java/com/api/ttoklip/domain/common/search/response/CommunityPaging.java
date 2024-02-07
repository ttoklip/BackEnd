package com.api.ttoklip.domain.common.search.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CommunityPaging(List<CommunitySingleResponse> communities, Integer totalPage,
                              Long totalElements, Boolean isFirst, Boolean isLast) {
}
