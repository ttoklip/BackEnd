package com.api.search.presentation.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NewsletterPaging(List<CommonThumbnailResponse> newsletters, Integer totalPage,
                               Long totalElements, Boolean isFirst, Boolean isLast) {
}
