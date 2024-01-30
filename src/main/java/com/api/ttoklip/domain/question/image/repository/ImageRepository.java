package com.api.ttoklip.domain.question.image.repository;

import com.api.ttoklip.domain.question.image.domain.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<QuestionImage, Long> {
}
