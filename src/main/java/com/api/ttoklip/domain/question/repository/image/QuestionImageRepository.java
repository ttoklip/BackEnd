package com.api.ttoklip.domain.question.repository.image;

import com.api.ttoklip.domain.question.domain.QuestionImage;

import java.util.List;

public interface QuestionImageRepository {

    QuestionImage save(final QuestionImage questionImage);

    boolean existsByQuestionIdAndUrl(Long questionId, String url);

    void verifyMemberIsImageOwner(List<Long> imageIds, Long memberId);

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);
}
