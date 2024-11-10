package com.api.newsletter.presentation.response;

import com.domain.newsletter.application.response.NewsletterThumbnailResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record NewsCategoryPagingResponse(List<NewsletterThumbnailResponse> newsletterThumbnailRespons,
                                         Integer totalPage,
                                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
