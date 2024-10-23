package com.api.ttoklip.domain.question.repository;


import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.domain.Question;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository {

    Question findByIdActivated(final Long questionId);

    QuestionComment findByCommentIdActivated(final Long commentId);

    Question findByIdFetchJoin(final Long questionPostId);

    List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId);

    Page<Question> matchCategoryPaging(final Category category, final Pageable pageable);

    List<Question> getHouseWork();

    List<Question> getRecipe();

    List<Question> getSafeLiving();

    List<Question> getWelfarePolicy();

    Question save(Question question);
}
