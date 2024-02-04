package com.api.ttoklip.domain.main.dto.response;

import java.util.List;

public record CommonSearchResponse(List<TitleResponse> searchData, Integer totalPage,
                                   Long totalElements, Boolean isFirst, Boolean isLast) {
}
