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
//    private final QuestionQueryRepository questionQueryRepository;

    @Override
    public Question findByIdActivated(final Long questionId) {
//        return questionQueryRepository.findByIdActivated(questionId);
        return null;
    }

    @Override
    public QuestionComment findByCommentIdActivated(final Long commentId) {
//        return questionQueryRepository.findByCommentIdActivated(commentId);
        return null;
    }

    @Override
    public Question findByIdFetchJoin(final Long questionPostId) {
//        return questionQueryRepository.findByIdFetchJoin(questionPostId);
        return null;
    }

    @Override
    public List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId) {
//        return questionQueryRepository.findActiveCommentsByQuestionId(questionId);
        return null;
    }

    @Override
    public Page<Question> matchCategoryPaging(final Category category, final Pageable pageable) {
//        return questionQueryRepository.matchCategoryPaging(category, pageable);
        return null;
    }

    @Override
    public List<Question> getHouseWork() {
//        return questionQueryRepository.getHouseWork();
        return null;
    }

    @Override
    public List<Question> getRecipe() {
//        return questionQueryRepository.getRecipe();
        return null;
    }

    @Override
    public List<Question> getSafeLiving() {
//        return questionQueryRepository.getSafeLiving();
        return null;
    }

    @Override
    public List<Question> getWelfarePolicy() {
//        return questionQueryRepository.getWelfarePolicy();
        return null;
    }

    @Override
    public Question save(final Question question) {
        return questionJpaRepository.save(question);
    }

    @Override
    public Page<Question> matchWriterPaging(final Long memberId, final Pageable pageable) {
//        return questionQueryRepository.matchWriterPaging(memberId, pageable);
        return null;
    }
}
