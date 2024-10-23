package com.api.ttoklip.domain.question.post.repository;

import com.api.ttoklip.domain.question.post.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJpaRepository extends JpaRepository<Question, Long> {
}
