package com.api.ttoklip.domain.question.post.repository;


import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.post.domain.Question;
import java.util.List;

public interface QuestionRepositoryCustom {
    Question findByIdFetchJoin(final Long questionPostId);
    List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId);
}
