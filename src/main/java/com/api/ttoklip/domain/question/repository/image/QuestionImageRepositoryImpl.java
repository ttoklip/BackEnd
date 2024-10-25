package com.api.ttoklip.domain.question.repository.image;

import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.domain.question.domain.QuestionImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionImageRepositoryImpl implements QuestionImageRepository {

    private final QuestionImageJpaRepository questionImageJpaRepository;

    @Override
    public QuestionImage save(final QuestionImage questionImage) {
        return questionImageJpaRepository.save(questionImage);
    }
}
