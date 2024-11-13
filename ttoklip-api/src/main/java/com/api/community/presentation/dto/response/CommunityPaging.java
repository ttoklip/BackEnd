package com.api.community.presentation.dto.response;

import com.api.town.application.TownCommunityResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record CommunityPaging(List<TownCommunityResponse> communities, Integer totalPage,
                              Long totalElements, Boolean isFirst, Boolean isLast) {
}
