package com.domain.question.infrastructure;

import com.domain.question.domain.QuestionImage;
import com.domain.question.domain.QuestionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionImageRepositoryImpl implements QuestionImageRepository {

    private final QuestionImageJpaRepository jpaRepository;
    private final QuestionImageQueryRepository queryDSLRepository;

    @Override
    public QuestionImage save(final QuestionImage questionImage) {
        return jpaRepository.save(questionImage);
    }

    @Override
    public boolean existsByQuestionIdAndUrl(final Long questionId, final String url) {
        return jpaRepository.existsByQuestionIdAndUrl(questionId, url);
    }

    @Override
    public void verifyMemberIsImageOwner(final List<Long> imageIds, final Long memberId) {
        queryDSLRepository.verifyMemberIsImageOwner(imageIds, memberId);
    }

    @Override
    public boolean doAllImageIdsExist(final List<Long> imageIds) {
        return queryDSLRepository.doAllImageIdsExist(imageIds);
    }

    @Override
    public void deleteByImageIds(final List<Long> imageIds) {
        queryDSLRepository.deleteByImageIds(imageIds);
    }


}
