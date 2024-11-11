package com.domain.question.domain;

import com.domain.common.comment.domain.CommentLike;
import java.util.Optional;

public interface QuestionCommentLikeRepository {

    Optional<CommentLike> findByQuestionCommentIdAndMemberId(Long commentId, Long memberId);

    boolean existsByQuestionCommentIdAndMemberId(Long commentId, Long memberId);

    void save(CommentLike commentLike);

    void deleteById(Long id);
}
