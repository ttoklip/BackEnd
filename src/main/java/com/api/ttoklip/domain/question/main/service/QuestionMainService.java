package com.api.ttoklip.domain.question.main.service;

import com.api.ttoklip.domain.question.main.dto.request.QuestionSearchCondition;
import com.api.ttoklip.domain.question.main.dto.response.QuestionCategoryResponse;
import com.api.ttoklip.domain.question.main.dto.response.QuestionSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionMainService {
    public QuestionSearchResponse search(QuestionSearchCondition condition, Pageable pageable) {
        return null;
    }

    public QuestionCategoryResponse category() {
        return null;
    }
}
