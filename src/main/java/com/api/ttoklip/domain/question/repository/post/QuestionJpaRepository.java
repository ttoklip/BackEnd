package com.api.ttoklip.domain.question.repository.post;

import com.api.ttoklip.domain.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
}
