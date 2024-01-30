package com.api.ttoklip.domain.question.post.repository;


import com.api.ttoklip.domain.question.post.domain.Question;

public interface QuestionRepositoryCustom {
    Question findByIdFetchJoin(final Long questionPostId);
}
