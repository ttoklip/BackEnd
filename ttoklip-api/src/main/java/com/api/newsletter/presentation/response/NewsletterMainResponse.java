package com.api.newsletter.presentation.response;

import com.domain.newsletter.application.response.NewsletterCategoryResponses;
import com.domain.newsletter.application.response.RandomTitleResponse;
import java.util.List;

public record NewsletterMainResponse(
        // 뉴스레터 요구사항 3번. 카테고리별 랜덤 게시글 4개를 리스트
        List<RandomTitleResponse> randomNews,
        // 뉴스레터 요구사항 5번. 카테고리별로 최신 게시글 10개
        NewsletterCategoryResponses categoryResponses
) {
    public static NewsletterMainResponse of(List<RandomTitleResponse> randomNews,
                                            NewsletterCategoryResponses categoryResponses) {
        return new NewsletterMainResponse(randomNews, categoryResponses);
    }
}
