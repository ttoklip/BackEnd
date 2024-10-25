package com.api.ttoklip.domain.question.repository.image;

import com.api.ttoklip.domain.question.domain.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionImageJpaRepository extends JpaRepository<QuestionImage, Long> {
}
