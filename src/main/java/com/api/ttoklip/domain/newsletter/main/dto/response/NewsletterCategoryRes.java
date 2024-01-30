package com.api.ttoklip.domain.newsletter.main.dto.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
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


    public static NewsletterCategoryRes toDto(Newsletter newsletter) {
        return NewsletterCategoryRes.builder()
                .newsletterId(newsletter.getId())
                .title(newsletter.getTitle())
                .category(newsletter.getCategory())
                .build();
    }
}
