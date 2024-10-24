package com.api.ttoklip.domain.question.repository.commentLike;

import com.api.ttoklip.domain.common.comment.CommentLike;

import java.util.Optional;

public interface CommentLikeRepository {

    Optional<CommentLike> findByQuestionCommentIdAndMemberId(Long commentId, Long memberId);

    boolean existsByQuestionCommentIdAndMemberId(Long commentId, Long memberId);

    void save(CommentLike commentLike);

    void deletedById(Long id);
}
