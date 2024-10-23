package com.api.ttoklip.domain.question.repository;

import com.api.ttoklip.domain.common.comment.CommentLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByQuestionCommentIdAndMemberId(Long commentId, Long memberId);

    boolean existsByQuestionCommentIdAndMemberId(Long commentId, Long memberId);

}
