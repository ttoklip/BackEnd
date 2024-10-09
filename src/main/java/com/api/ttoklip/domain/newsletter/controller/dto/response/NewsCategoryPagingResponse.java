package com.api.ttoklip.domain.newsletter.controller.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NewsCategoryPagingResponse(List<NewsletterThumbnailResponse> newsletterThumbnailRespons,
                                         Integer totalPage,
                                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
