package com.domain.question.repository;

import com.domain.comment.domain.CommentLike;
import com.domain.question.domain.QuestionCommentLikeRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeQuestionCommentLikeRepository implements QuestionCommentLikeRepository {

    private final Map<Long, CommentLike> questionCommentLikeRepository = new HashMap<>();
    private Long commentLikeId = 0L;

    @Override
    public Optional<CommentLike> findByQuestionCommentIdAndMemberId(final Long commentId, final Long memberId) {
        return questionCommentLikeRepository.values().stream()
                .filter(like -> like.getQuestionComment().getId().equals(commentId)
                        && like.getMember().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByQuestionCommentIdAndMemberId(final Long commentId, final Long memberId) {
        return questionCommentLikeRepository.values().stream()
                .anyMatch(like -> like.getQuestionComment().getId().equals(commentId)
                        && like.getMember().getId().equals(memberId));
    }

    @Override
    public void save(final CommentLike commentLike) {
        commentLikeId++;
        CommentLike savedCommentLike = CommentLike.builder()
                .id(commentLikeId)
                .member(commentLike.getMember())
                .questionComment(commentLike.getQuestionComment())
                .build();

        questionCommentLikeRepository.put(commentLikeId, savedCommentLike);
    }

    @Override
    public void deleteById(final Long id) {
        questionCommentLikeRepository.remove(id);
    }
}
