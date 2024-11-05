package com.api.ttoklip.domain.question.repository.commentLike;

import com.api.ttoklip.domain.common.comment.CommentLike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCommentLikeJpaRepository extends JpaRepository<CommentLike, Long> {
}
