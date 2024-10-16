package com.api.ttoklip.domain.newsletter.controller.dto.response;

import java.util.List;

public record NewsletterCategoryResponses(
        List<NewsletterThumbnailResponse> houseWork,
        List<NewsletterThumbnailResponse> recipe,
        List<NewsletterThumbnailResponse> safeLiving,
        List<NewsletterThumbnailResponse> welfarePolicy
) {
    public static NewsletterCategoryResponses of(List<NewsletterThumbnailResponse> houseWork,
                                                 List<NewsletterThumbnailResponse> recipe,
                                                 List<NewsletterThumbnailResponse> safeLiving,
                                                 List<NewsletterThumbnailResponse> welfarePolicy) {
        return new NewsletterCategoryResponses(houseWork, recipe, safeLiving, welfarePolicy);
    }
}
