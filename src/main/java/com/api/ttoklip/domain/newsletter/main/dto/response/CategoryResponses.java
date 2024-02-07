package com.api.ttoklip.domain.newsletter.main.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponses {
    private List<CategoryResponse> houseWork;
    private List<CategoryResponse> recipe;
    private List<CategoryResponse> safeLiving;
    private List<CategoryResponse> welfarePolicy;

    public static CategoryResponses of(final List<CategoryResponse> houseWork,
                                       final List<CategoryResponse> recipe,
                                       final List<CategoryResponse> safeLiving,
                                       final List<CategoryResponse> welfarePolicy) {
        return CategoryResponses.builder()
                .houseWork(houseWork)
                .recipe(recipe)
                .safeLiving(safeLiving)
                .welfarePolicy(welfarePolicy)
                .build();
    }
}
