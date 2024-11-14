package com.domain.common.vo;

import com.domain.honeytip.domain.HoneyTipImage;
import com.domain.question.domain.QuestionImage;
import lombok.Builder;

@Builder
public record ImageResponse(Long imageId, String imageUrl) {

    public static ImageResponse questionFrom(final QuestionImage image) {
        return builder()
                .imageId(image.getId())
                .imageUrl(image.getUrl())
                .build();
    }

    public static ImageResponse honeyTipFrom(final HoneyTipImage image) {
        return builder()
                .imageId(image.getId())
                .imageUrl(image.getUrl())
                .build();
    }

}
