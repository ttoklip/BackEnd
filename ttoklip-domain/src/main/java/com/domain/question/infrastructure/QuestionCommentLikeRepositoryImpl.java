package com.domain.question.infrastructure;

import com.domain.common.comment.domain.CommentLike;
import com.domain.question.domain.QuestionCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionCommentLikeRepositoryImpl implements QuestionCommentLikeRepository {

    private final QuestionCommentLikeQueryRepository questionCommentLikeQueryRepository;
    private final QuestionCommentLikeJpaRepository questionCommentLikeJpaRepository;

    @Override
    public Optional<CommentLike> findByQuestionCommentIdAndMemberId(Long commentId, Long memberId) {
        return questionCommentLikeQueryRepository.findByQuestionCommentIdAndMemberId(commentId, memberId);
    }

    @Override
    public boolean existsByQuestionCommentIdAndMemberId(Long commentId, Long memberId) {
        return questionCommentLikeQueryRepository.existsByQuestionCommentIdAndMemberId(commentId, memberId);
    }

    @Override
    public void save(final CommentLike commentLike) {
        questionCommentLikeJpaRepository.save(commentLike);
    }

    @Override
    public void deleteById(final Long id) {
        questionCommentLikeJpaRepository.deleteById(id);
    }

}
