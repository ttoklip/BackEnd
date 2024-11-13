package com.domain.question.domain;

import com.domain.common.vo.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository {

    Question findByIdActivated(Long questionId);

    QuestionComment findByCommentIdActivated(Long commentId);

    Question findByIdFetchJoin(Long questionPostId);

    List<QuestionComment> findActiveCommentsByQuestionId(Long questionId);

    Page<Question> matchCategoryPaging(Category category, Pageable pageable);

    List<Question> getHouseWork();

    List<Question> getRecipe();

    List<Question> getSafeLiving();

    List<Question> getWelfarePolicy();

    Question save(Question question);

    Page<Question> matchWriterPaging(Long memberId, Pageable pageable);
}
