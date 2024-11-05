package com.api.ttoklip.domain.question.service;

import com.api.ttoklip.domain.common.comment.CommentLike;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.repository.commentLike.QuestionCommentLikeRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
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
