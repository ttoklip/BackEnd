package com.domain.common.vo;

import com.domain.honeytip.domain.HoneyTipImage;
import com.domain.question.domain.QuestionImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResponse {

    private Long imageId;

    private String imageUrl;

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
