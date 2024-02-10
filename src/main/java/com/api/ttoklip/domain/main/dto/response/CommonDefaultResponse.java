package com.api.ttoklip.domain.main.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommonDefaultResponse {

    private CategoryResponses questionCategory;
    private CategoryResponses honeyTipCategory;
    private List<TitleResponse> topFiveQuestions;

    // ToDo 좋아요 구현 완료, 댓글 수 구현 완료, 스크랩 구현 후에 더하여 계산하는 로직 필요
    public static CommonDefaultResponse of(final CategoryResponses question,
                                           final CategoryResponses honeyTips,
                                           final List<TitleResponse> titleResponses) {
        return CommonDefaultResponse.builder()
                .questionCategory(question)
                .honeyTipCategory(honeyTips)
//                .topFiveQuestions(titleResponses)
                .build();
    }
}