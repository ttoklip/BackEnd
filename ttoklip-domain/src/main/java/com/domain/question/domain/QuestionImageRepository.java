package com.domain.question.domain;

import java.util.List;

public interface QuestionImageRepository {

    QuestionImage save(final QuestionImage questionImage);

    boolean existsByQuestionIdAndUrl(Long questionId, String url);

    void verifyMemberIsImageOwner(List<Long> imageIds, Long memberId);

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);
}
