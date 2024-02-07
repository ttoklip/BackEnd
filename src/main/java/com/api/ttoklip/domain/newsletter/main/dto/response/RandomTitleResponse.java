package com.api.ttoklip.domain.newsletter.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RandomTitleResponse {

    private Long newsletterId;
    private String title;
    private String url;
}
