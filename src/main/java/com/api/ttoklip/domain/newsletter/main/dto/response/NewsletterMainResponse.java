package com.api.ttoklip.domain.newsletter.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class NewsletterMainResponse {

    // 3번. 카테고리별 랜덤 게시글 4개를 리스트로 반환한다.
    private List<RandomTitleResponse> randomNews;

    // 5번. 최신 게시글 3개를 출력한다.
    private List<CategoryResponse> topThreeNewsletters;
}
