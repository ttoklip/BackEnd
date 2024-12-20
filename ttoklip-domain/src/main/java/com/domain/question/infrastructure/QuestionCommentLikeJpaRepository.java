package com.domain.question.infrastructure;

import com.domain.comment.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCommentLikeJpaRepository extends JpaRepository<CommentLike, Long> {
}
