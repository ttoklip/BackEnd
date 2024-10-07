package com.api.ttoklip.domain.newsletter.controller.dto.response;

import com.api.ttoklip.domain.newsletter.domain.NewsletterUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlRes {

    @Schema(description = "Url 링크")
    private String Url;

    public static UrlRes toDto(final NewsletterUrl newsletterUrl) {
        return UrlRes.builder()
                .Url(newsletterUrl.getUrl())
                .build();
    }
}
