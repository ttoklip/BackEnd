package com.api.ttoklip.domain.newsletter.controller.dto.response;

import com.api.ttoklip.domain.newsletter.domain.TodayNewsletter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RandomTitleResponse {

    private Long newsletterId;
    private String title;
    private String mainImageUrl;

    public static RandomTitleResponse of(final TodayNewsletter todayNewsletter) {
        return RandomTitleResponse.builder()
                .newsletterId(todayNewsletter.getNewsletter().getId())
                .title(todayNewsletter.getNewsletter().getTitle())
                .mainImageUrl(todayNewsletter.getNewsletter().getMainImageUrl())
                .build();
    }
}
