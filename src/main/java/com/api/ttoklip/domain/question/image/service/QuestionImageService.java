package com.api.ttoklip.domain.question.image.service;

import com.api.ttoklip.domain.question.image.domain.QuestionImage;
import com.api.ttoklip.domain.question.image.repository.QuestionImageRepository;
import com.api.ttoklip.domain.question.post.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionImageService {
    private final QuestionImageRepository questionImageRepository;

    public void register(final Question question, final String uploadUrl) {
        QuestionImage newQuestionImage = QuestionImage.of(question, uploadUrl);
        questionImageRepository.save(newQuestionImage);
    }
}
