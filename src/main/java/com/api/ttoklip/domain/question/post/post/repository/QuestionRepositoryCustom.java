package com.api.ttoklip.domain.question.post.post.repository;

import com.api.ttoklip.domain.question.post.post.domain.Question;

public interface QuestionRepositoryCustom {
    Question findByIdUndeleted(final Long questionId);
}
