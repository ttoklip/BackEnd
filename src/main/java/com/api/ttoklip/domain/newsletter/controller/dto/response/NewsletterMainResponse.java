package com.api.ttoklip.domain.newsletter.controller.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsletterMainResponse {

    // 3번. 카테고리별 랜덤 게시글 4개를 리스트로 반환한다.
    private List<RandomTitleResponse> randomNews;

    // 5번. 카테고리별로 최신 게시글 10개를 출력한다.
    private CategoryResponses categoryResponses;

    public static NewsletterMainResponse of(final List<RandomTitleResponse> randomNews,
                                            final CategoryResponses categoryResponses) {
        return NewsletterMainResponse.builder()
                .randomNews(randomNews)
                .categoryResponses(categoryResponses)
                .build();
    }
}
