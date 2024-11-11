package com.domain.question.repository;

import com.api.ttoklip.domain.question.domain.QuestionImage;
import com.api.ttoklip.domain.question.domain.QuestionImageRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeQuestionImageRepository implements QuestionImageRepository {

    private final Map<Long, QuestionImage> questionImageMap = new HashMap<>();
    private Long questionImageId = 0L;

    @Override
    public QuestionImage save(final QuestionImage questionImage) {
        questionImageId++;  // ID 자동 증가
        QuestionImage savedQuestionImage = QuestionImage.builder()
                .id(questionImageId)
                .question(questionImage.getQuestion())
                .url(questionImage.getUrl())
                .build();

        questionImageMap.put(questionImageId, savedQuestionImage);
        return savedQuestionImage;
    }

    @Override
    public boolean existsByQuestionIdAndUrl(final Long questionId, final String url) {
        return questionImageMap.values().stream()
                .anyMatch(questionImage -> questionImage.getQuestion().getId().equals(questionId) &&
                        questionImage.getUrl().equals(url));
    }

    @Override
    public void verifyMemberIsImageOwner(final List<Long> imageIds, final Long memberId) {
        List<QuestionImage> questionImages = questionImageMap.values().stream()
                .filter(questionImage -> imageIds.contains(questionImage.getId()))
                .collect(Collectors.toList());

        boolean isOwner = questionImages.stream()
                .allMatch(image -> image.getQuestion().getMember().getId().equals(memberId));

        if (!isOwner) {
            throw new ApiException(ErrorType.INVALID_DELETE_IMAGE_OWNER);
        }
    }

    @Override
    public boolean doAllImageIdsExist(final List<Long> imageIds) {
        long count = questionImageMap.values().stream()
                .filter(image -> imageIds.contains(image.getId()))
                .count();

        return count == imageIds.size();
    }

    @Override
    public void deleteByImageIds(final List<Long> imageIds) {
        Map<Long, QuestionImage> backup = new HashMap<>(questionImageMap);

        try {
            imageIds.forEach(questionImageMap::remove);
        } catch (Exception e) {
            // 예외 발생 시 롤백
            questionImageMap.clear();
            questionImageMap.putAll(backup);

            throw new RuntimeException("이미지 삭제 중 예외 발생", e);
        }
    }
}
