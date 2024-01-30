package com.api.ttoklip.domain.question.post.image.service;

import com.api.ttoklip.domain.question.post.image.domain.QuestionImage;
import com.api.ttoklip.domain.question.post.image.repository.ImageRepository;
import com.api.ttoklip.domain.question.post.post.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void register(final Question question, final String uploadUrl) {
        QuestionImage newQuestionImage = QuestionImage.of(question, uploadUrl);
        imageRepository.save(newQuestionImage);
    }
}
