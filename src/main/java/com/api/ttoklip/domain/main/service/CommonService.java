package com.api.ttoklip.domain.main.service;

import com.api.ttoklip.domain.honeytip.post.service.HoneyTipPostService;
import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
import com.api.ttoklip.domain.main.dto.response.CommonDefaultResponse;
import com.api.ttoklip.domain.question.post.service.QuestionPostService;
import lombok.RequiredArgsConstructor;
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
//        honeyTipPostService.getTop5();

        return CommonDefaultResponse.of(questionCategoryResponse, honeyTipCategoryResponse, null);
    }


}
