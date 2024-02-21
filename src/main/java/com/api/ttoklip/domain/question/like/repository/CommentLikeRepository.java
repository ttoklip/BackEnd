package com.api.ttoklip.domain.question.like.repository;

import com.api.ttoklip.domain.question.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>, CommentLikeRepositoryCustom {

    Optional<CommentLike> findByQuestionCommentIdAndMemberId(Long commentId, Long memberId);

    boolean existsByQuestionCommentIdAndMemberId(Long commentId, Long memberId);

}
