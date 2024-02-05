package com.api.ttoklip.domain.honeytip.url.dto.response;

import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
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
        return UrlResponse.builder()
                .urls(url.getUrl())
                .build();
    }
}
