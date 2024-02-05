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