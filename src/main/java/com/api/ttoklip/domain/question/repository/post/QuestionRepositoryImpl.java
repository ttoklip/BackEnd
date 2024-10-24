package com.api.ttoklip.domain.question.repository.post;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.domain.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaRepository jpaRepository;
    private final QuestionQueryRepository queryDslRepository;

    @Override
    public Question findByIdActivated(final Long questionId) {
        return queryDslRepository.findByIdActivated(questionId);
    }

    @Override
    public QuestionComment findByCommentIdActivated(final Long commentId) {
        return queryDslRepository.findByCommentIdActivated(commentId);
    }

    @Override
    public Question findByIdFetchJoin(final Long questionPostId) {
        return queryDslRepository.findByIdFetchJoin(questionPostId);
    }

    @Override
    public List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId) {
        return queryDslRepository.findActiveCommentsByQuestionId(questionId);
    }

    @Override
    public Page<Question> matchCategoryPaging(final Category category, final Pageable pageable) {
        return queryDslRepository.matchCategoryPaging(category, pageable);
    }

    @Override
    public List<Question> getHouseWork() {
        return queryDslRepository.getHouseWork();
    }

    @Override
    public List<Question> getRecipe() {
        return queryDslRepository.getRecipe();
    }

    @Override
    public List<Question> getSafeLiving() {
        return queryDslRepository.getSafeLiving();
    }

    @Override
    public List<Question> getWelfarePolicy() {
        return queryDslRepository.getWelfarePolicy();
    }

    @Override
    public Question save(final Question question) {
        return jpaRepository.save(question);
    }
}
