package com.api.ttoklip.domain.main.service;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.facade.HoneyTipPostFacade;
import com.api.ttoklip.domain.main.dto.response.CategoryPagingResponse;
import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
import com.api.ttoklip.domain.main.dto.response.CommonDefaultResponse;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.question.facade.QuestionPostFacade;
import com.api.ttoklip.domain.question.service.QuestionPostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommonService {

    private final QuestionPostService questionPostService;
    private final QuestionPostFacade questionPostFacade;
    private final HoneyTipPostFacade honeyTipPostFacade;

    /* -------------------------------------------- 카토고리별 MAIN READ -------------------------------------------- */

    public CommonDefaultResponse getDefaultCategoryRead() {
        CategoryResponses questionCategoryResponse = questionPostFacade.getDefaultCategoryRead();
        CategoryResponses honeyTipCategoryResponse = honeyTipPostFacade.getDefaultCategoryRead();
        List<TitleResponse> top5Responses = honeyTipPostFacade.getPopularityTop5();

        return CommonDefaultResponse.of(questionCategoryResponse, honeyTipCategoryResponse, top5Responses);
    }

    /* -------------------------------------------- 카토고리별 MAIN READ 끝 -------------------------------------------- */


    /* -------------------------------------------- 카토고리별 MAIN READ - 카테고리별 페이징 -------------------------------------------- */
    public CategoryPagingResponse questionCategoryPaging(final String categoryInput, final Pageable pageable) {
        Category category = Category.findCategoryByValue(categoryInput);
        return questionPostFacade.matchCategoryPaging(category, pageable);
    }

    public CategoryPagingResponse honeyTipCategoryPaging(final String categoryInput, final Pageable pageable) {
        Category category = Category.findCategoryByValue(categoryInput);
        return honeyTipPostFacade.matchCategoryPaging(category, pageable);
    }

    /* -------------------------------------------- 카토고리별 MAIN READ - 카테고리별 페이징 끝 -------------------------------------------- */
}
