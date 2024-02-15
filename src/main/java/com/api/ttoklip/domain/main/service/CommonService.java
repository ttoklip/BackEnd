package com.api.ttoklip.domain.main.service;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipPostService;
import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
import com.api.ttoklip.domain.main.dto.response.CommonDefaultResponse;
import com.api.ttoklip.domain.main.dto.response.QuestionCategoryPagingResponse;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.question.post.service.QuestionPostService;
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
    private final HoneyTipPostService honeyTipPostService;

    /* -------------------------------------------- 카토고리별 MAIN READ -------------------------------------------- */

    public CommonDefaultResponse getDefaultCategoryRead() {
        CategoryResponses questionCategoryResponse = questionPostService.getDefaultCategoryRead();
        CategoryResponses honeyTipCategoryResponse = honeyTipPostService.getDefaultCategoryRead();
        List<TitleResponse> top5Responses = honeyTipPostService.getTop5();

        return CommonDefaultResponse.of(questionCategoryResponse, honeyTipCategoryResponse, top5Responses);
    }

    /* -------------------------------------------- 카토고리별 MAIN READ 끝 -------------------------------------------- */


    /* -------------------------------------------- 카토고리별 MAIN READ - 카테고리별 페이징 -------------------------------------------- */
    public QuestionCategoryPagingResponse getCategoryPaging(final String categoryInput, final Pageable pageable) {
        Category category = Category.findCategoryByValue(categoryInput);
        return questionPostService.matchCategoryPaging(category, pageable);
    }

    /* -------------------------------------------- 카토고리별 MAIN READ - 카테고리별 페이징 끝 -------------------------------------------- */
}
