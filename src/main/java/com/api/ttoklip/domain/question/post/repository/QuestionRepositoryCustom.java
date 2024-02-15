package com.api.ttoklip.domain.question.post.repository;


import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.post.domain.Question;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {
    Question findByIdFetchJoin(final Long questionPostId);
    List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId);
    Page<Question> matchCategoryPaging(final Category category, final Pageable pageable);
}
