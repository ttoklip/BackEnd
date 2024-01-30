package com.api.ttoklip.domain.question.post.post.repository;

import com.api.ttoklip.domain.question.post.post.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
