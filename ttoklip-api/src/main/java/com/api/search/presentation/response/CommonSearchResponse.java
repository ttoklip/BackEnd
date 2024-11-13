package com.api.search.presentation.response;

import com.domain.common.vo.TitleResponse;
import java.util.List;

public record CommonSearchResponse(List<TitleResponse> searchData, Integer totalPage,
                                   Long totalElements, Boolean isFirst, Boolean isLast) {
}
