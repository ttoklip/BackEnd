package com.api.ttoklip.domain.question.repository.commentLike;

import com.api.ttoklip.domain.common.comment.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepository {

    private final CommentLikeQueryRepository commentLikeQueryRepository;
    private final CommentLikeJpaRepository commentLikeJpaRepository;

    @Override
    public Optional<CommentLike> findByQuestionCommentIdAndMemberId(Long commentId, Long memberId) {
        return commentLikeQueryRepository.findByQuestionCommentIdAndMemberId(commentId, memberId);
    }

    @Override
    public boolean existsByQuestionCommentIdAndMemberId(Long commentId, Long memberId) {
        return commentLikeQueryRepository.existsByQuestionCommentIdAndMemberId(commentId, memberId);
    }

    @Override
    public void save(final CommentLike commentLike) {
        commentLikeJpaRepository.save(commentLike);
    }

    @Override
    public void deletedById(final Long id) {
        commentLikeJpaRepository.deleteById(id);
    }
}
