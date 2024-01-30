package com.api.ttoklip.domain.newsletter.main.dto.response;

import com.api.ttoklip.domain.common.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NewsletterCategoryRes {
    private Long newsletterId;
    private String title;
    private Category category;
}
