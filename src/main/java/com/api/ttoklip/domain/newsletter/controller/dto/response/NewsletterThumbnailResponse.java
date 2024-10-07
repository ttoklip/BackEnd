package com.api.ttoklip.domain.newsletter.controller.dto.response;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NewsletterThumbnailResponse {
    private Long newsletterId;
    private String title;
    private String mainImageUrl;
    private String writtenTime;

    public static NewsletterThumbnailResponse from(final Newsletter newsletter) {
        String writtenTime = TimeUtil.newsletterTimeFormat(newsletter.getCreatedDate());

        return NewsletterThumbnailResponse.builder()
                .newsletterId(newsletter.getId())
                .title(newsletter.getTitle())
                .mainImageUrl(newsletter.getMainImageUrl())
                .writtenTime(writtenTime)
                // todo 댓글 수 추가
                .build();
    }
}
