package com.domain.question.infrastructure;

import com.domain.question.domain.QuestionImage;
import com.domain.question.domain.QuestionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionImageRepositoryImpl implements QuestionImageRepository {

    private final QuestionImageJpaRepository questionImageJpaRepository;
//    private final QuestionImageQueryRepository questionImageQueryRepository;

    @Override
    public QuestionImage save(final QuestionImage questionImage) {
        return questionImageJpaRepository.save(questionImage);
    }

    @Override
    public boolean existsByQuestionIdAndUrl(final Long questionId, final String url) {
        return questionImageJpaRepository.existsByQuestionIdAndUrl(questionId, url);
    }

    @Override
    public void verifyMemberIsImageOwner(final List<Long> imageIds, final Long memberId) {
//        questionImageQueryRepository.verifyMemberIsImageOwner(imageIds, memberId);
    }

    @Override
    public boolean doAllImageIdsExist(final List<Long> imageIds) {
//        return questionImageQueryRepository.doAllImageIdsExist(imageIds);
        return false;
    }

    @Override
    public void deleteByImageIds(final List<Long> imageIds) {
//        questionImageQueryRepository.deleteByImageIds(imageIds);
    }


}
