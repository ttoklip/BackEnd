package com.api.ttoklip.domain.question.service;

import com.api.ttoklip.domain.question.dto.request.QuestionSearchCondition;
import com.api.ttoklip.domain.question.dto.response.QuestionCategoryResponse;
import com.api.ttoklip.domain.question.dto.response.QuestionSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    public QuestionSearchResponse search(QuestionSearchCondition condition, Pageable pageable) {
        return null;
    }

    public QuestionCategoryResponse category() {
        return null;
    }
}
