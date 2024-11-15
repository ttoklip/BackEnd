package com.domain.question.infrastructure;

import com.domain.question.domain.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionImageJpaRepository extends JpaRepository<QuestionImage, Long> {

    boolean existsByQuestionIdAndUrl(Long questionId, String url);
}
