package com.api.search.presentation.response;

import java.util.List;
import lombok.Builder;

@Builder
public record HoneyTipPaging(List<SingleResponse> honeyTips, Integer totalPage,
                             Long totalElements, Boolean isFirst, Boolean isLast) {
}
