package com.domain.question.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.comment.domain.CommentLike;
import com.domain.member.domain.Member;
import com.domain.question.domain.QuestionComment;
import com.domain.question.domain.QuestionCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommentLikeService {

    private final QuestionCommentLikeRepository questionCommentLikeRepository;

    public boolean isCommentLikeExists(final Long questionCommentId, final Long currentMemberId) {
        return questionCommentLikeRepository.existsByQuestionCommentIdAndMemberId(questionCommentId, currentMemberId);
    }

    // 좋아요 생성
    @Transactional
    public void registerLike(final QuestionComment questionComment, final Member currentMember) {
        boolean exists = existsByQuestionCommentIdAndMemberId(questionComment.getId(), currentMember.getId());
        // 이미 스크랩이 존재하면 좋아요를 생성하지 않고 return
        if (exists) {
            return;
        }

        CommentLike commentLike = CommentLike.from(currentMember, questionComment);
        questionCommentLikeRepository.save(commentLike);
    }

    public boolean existsByQuestionCommentIdAndMemberId(final Long commentId, final Long currentMemberId) {
        return questionCommentLikeRepository.existsByQuestionCommentIdAndMemberId(commentId, currentMemberId);
    }

    // 좋아요 취소
    @Transactional
    public void cancelLike(final QuestionComment findQuestionComment, final Long memberId) {
        Long findQuestionCommentId = findQuestionComment.getId();
        CommentLike commentLike = questionCommentLikeRepository.findByQuestionCommentIdAndMemberId(findQuestionCommentId, memberId)
                .orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));
        questionCommentLikeRepository.deleteById(commentLike.getId());
    }
}
