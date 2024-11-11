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

    private final QuestionJpaRepository questionJpaRepository;
    private final QuestionQueryRepository questionQueryRepository;

    @Override
    public Question findByIdActivated(final Long questionId) {
        return questionQueryRepository.findByIdActivated(questionId);
    }

    @Override
    public QuestionComment findByCommentIdActivated(final Long commentId) {
        return questionQueryRepository.findByCommentIdActivated(commentId);
    }

    @Override
    public Question findByIdFetchJoin(final Long questionPostId) {
        return questionQueryRepository.findByIdFetchJoin(questionPostId);
    }

    @Override
    public List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId) {
        return questionQueryRepository.findActiveCommentsByQuestionId(questionId);
    }

    @Override
    public Page<Question> matchCategoryPaging(final Category category, final Pageable pageable) {
        return questionQueryRepository.matchCategoryPaging(category, pageable);
    }

    @Override
    public List<Question> getHouseWork() {
        return questionQueryRepository.getHouseWork();
    }

    @Override
    public List<Question> getRecipe() {
        return questionQueryRepository.getRecipe();
    }

    @Override
    public List<Question> getSafeLiving() {
        return questionQueryRepository.getSafeLiving();
    }

    @Override
    public List<Question> getWelfarePolicy() {
        return questionQueryRepository.getWelfarePolicy();
    }

    @Override
    public Question save(final Question question) {
        return questionJpaRepository.save(question);
    }
}
