package com.domain.question.infrastructure;

import com.domain.comment.domain.CommentLike;
import com.domain.question.domain.QuestionCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionCommentLikeRepositoryImpl implements QuestionCommentLikeRepository {

    private final QuestionCommentLikeJpaRepository jpaRepository;
    private final QuestionCommentLikeQueryRepository queryDSLRepository;

    @Override
    public Optional<CommentLike> findByQuestionCommentIdAndMemberId(Long commentId, Long memberId) {
        return queryDSLRepository.findByQuestionCommentIdAndMemberId(commentId, memberId);
    }

    @Override
    public boolean existsByQuestionCommentIdAndMemberId(Long commentId, Long memberId) {
        return queryDSLRepository.existsByQuestionCommentIdAndMemberId(commentId, memberId);
    }

    @Override
    public void save(final CommentLike commentLike) {
        jpaRepository.save(commentLike);
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }

}
