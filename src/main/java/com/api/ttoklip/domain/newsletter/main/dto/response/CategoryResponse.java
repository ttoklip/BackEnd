package com.api.ttoklip.domain.newsletter.main.dto.response;

import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponse {
    private Long newsletterId;
    private String title;
    private String mainImageUrl;
    private String writtenTime;

    public static CategoryResponse of(final Newsletter newsletter) {
        String writtenTime = TimeUtil.newsletterTimeFormat(newsletter.getCreatedDate());

        return CategoryResponse.builder()
                .newsletterId(newsletter.getId())
                .title(newsletter.getTitle())
                .mainImageUrl(newsletter.getMainImageUrl())
                .writtenTime(writtenTime)
                .build();
    }
}
