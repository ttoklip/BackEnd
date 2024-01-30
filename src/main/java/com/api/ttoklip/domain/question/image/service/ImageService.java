package com.api.ttoklip.domain.question.image.service;

import com.api.ttoklip.domain.question.image.domain.QuestionImage;
import com.api.ttoklip.domain.question.image.repository.ImageRepository;
import com.api.ttoklip.domain.question.post.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public void register(final Question question, final String uploadUrl) {
        QuestionImage newQuestionImage = QuestionImage.of(question, uploadUrl);
        imageRepository.save(newQuestionImage);
    }
}
