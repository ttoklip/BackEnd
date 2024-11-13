package com.api.home.presentation.response;

import com.domain.common.vo.CategoryResponses;
import com.domain.common.vo.TitleResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record HomeCategoryAndTopQuestionsResponse(
        CategoryResponses questionCategory,
        CategoryResponses honeyTipCategory,
        List<TitleResponse> topFiveQuestions
) {
    public static HomeCategoryAndTopQuestionsResponse of(final CategoryResponses question,
                                                         final CategoryResponses honeyTips,
                                                         final List<TitleResponse> titleResponses) {
        return builder()
                .questionCategory(question)
                .honeyTipCategory(honeyTips)
                .topFiveQuestions(titleResponses)
                .build();
    }
}