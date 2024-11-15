package com.api.honeytip.presentation.response;

import com.domain.honeytip.domain.HoneyTipUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlResponse {

    @Schema(description = "채팅 주소 url")
    private String urls;

    public static UrlResponse honeyTipUrlFrom(final HoneyTipUrl url) {
        return builder()
                .urls(url.getUrl())
                .build();
    }
}
