package com.api.ttoklip.domain.newsletter.controller.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponses {
    private List<NewsletterThumbnailResponse> houseWork;
    private List<NewsletterThumbnailResponse> recipe;
    private List<NewsletterThumbnailResponse> safeLiving;
    private List<NewsletterThumbnailResponse> welfarePolicy;

    public static CategoryResponses of(final List<NewsletterThumbnailResponse> houseWork,
                                       final List<NewsletterThumbnailResponse> recipe,
                                       final List<NewsletterThumbnailResponse> safeLiving,
                                       final List<NewsletterThumbnailResponse> welfarePolicy) {
        return CategoryResponses.builder()
                .houseWork(houseWork)
                .recipe(recipe)
                .safeLiving(safeLiving)
                .welfarePolicy(welfarePolicy)
                .build();
    }
}
