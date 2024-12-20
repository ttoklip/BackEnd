package com.domain.question.infrastructure;

import com.domain.common.vo.Category;
import com.domain.question.domain.QuestionComment;
import com.domain.question.domain.Question;
import com.domain.question.domain.QuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaRepository jpaRepository;
    private final QuestionQueryRepository queryDSLRepository;

    @Override
    public Question findByIdActivated(final Long questionId) {
        return queryDSLRepository.findByIdActivated(questionId);
    }

    @Override
    public QuestionComment findByCommentIdActivated(final Long commentId) {
        return queryDSLRepository.findByCommentIdActivated(commentId);
    }

    @Override
    public Question findByIdFetchJoin(final Long questionPostId) {
        return queryDSLRepository.findByIdFetchJoin(questionPostId);
    }

    @Override
    public List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId) {
        return queryDSLRepository.findActiveCommentsByQuestionId(questionId);
    }

    @Override
    public Page<Question> matchCategoryPaging(final Category category, final Pageable pageable) {
        return queryDSLRepository.matchCategoryPaging(category, pageable);
    }

    @Override
    public List<Question> getHouseWork() {
        return queryDSLRepository.getHouseWork();
    }

    @Override
    public List<Question> getRecipe() {
        return queryDSLRepository.getRecipe();
    }

    @Override
    public List<Question> getSafeLiving() {
        return queryDSLRepository.getSafeLiving();
    }

    @Override
    public List<Question> getWelfarePolicy() {
        return queryDSLRepository.getWelfarePolicy();
    }

    @Override
    public Question save(final Question question) {
        return jpaRepository.save(question);
    }

    @Override
    public Page<Question> matchWriterPaging(final Long memberId, final Pageable pageable) {
        return queryDSLRepository.matchWriterPaging(memberId, pageable);
    }
}
