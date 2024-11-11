package com.domain.question.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.question.domain.QuestionImage;
import com.domain.question.domain.QuestionImageRepository;
import com.domain.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionImageService {
    private final QuestionImageRepository questionImageRepository;

    public void register(final Question question, final String uploadUrl) {
        QuestionImage newQuestionImage = QuestionImage.of(question, uploadUrl);
        questionImageRepository.save(newQuestionImage);
    }

    @Transactional
    public void deleteImages(final List<Long> imageIds, final Long currentMemberId) {
        validImagesExists(imageIds);
        questionImageRepository.verifyMemberIsImageOwner(imageIds, currentMemberId);
        questionImageRepository.deleteByImageIds(imageIds);
    }

    // 기존 이미지가 DB에 존재하는 이미지들인지?
    private void validImagesExists(final List<Long> imageIds) {
        boolean allImageIdsExist = questionImageRepository.doAllImageIdsExist(imageIds);
        if (!allImageIdsExist) {
            throw new ApiException(ErrorType.DELETE_INVALID_IMAGE_IDS);
        }
    }
}
