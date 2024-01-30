package com.api.ttoklip.domain.main.service;

import com.api.ttoklip.domain.main.dto.request.QuestionSearchCondition;
import com.api.ttoklip.domain.main.dto.response.QuestionMainDefaultResponse;
import com.api.ttoklip.domain.main.dto.response.QuestionSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionMainService {
    public QuestionSearchResponse search(final QuestionSearchCondition condition, final Pageable pageable) {
        return null;
    }

    public QuestionMainDefaultResponse main() {
        return null;
    }
}
