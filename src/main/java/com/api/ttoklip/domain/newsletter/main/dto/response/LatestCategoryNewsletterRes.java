package com.api.ttoklip.domain.newsletter.main.dto.response;

import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LatestCategoryNewsletterRes {
    private Long newsletterId;
    private String title;
    private String url;
    private String writtenTime;

    public static LatestCategoryNewsletterRes toDto(Newsletter newsletter) {
        return LatestCategoryNewsletterRes.builder()
                .newsletterId(newsletter.getId())
                .title(newsletter.getTitle())
                .url(newsletter.getNewsletterImageList().get(0).getUrl())
                .writtenTime(newsletter.getCreatedDate().toString())
                .build();
    }
}
