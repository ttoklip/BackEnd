package com.api.ttoklip.domain.question.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.comment.CommentLike;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.repository.commentLike.CommentLikeRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    // 좋아요 생성
    @Transactional
    public void registerLike(final QuestionComment questionComment, final Member currentMember) {
        boolean exists = existsByQuestionCommentIdAndMemberId(questionComment.getId(), currentMember.getId());
        // 이미 스크랩이 존재하면 좋아요를 생성하지 않고 return
        if (exists) {
            return;
        }

        CommentLike commentLike = CommentLike.from(currentMember, questionComment);
        commentLikeRepository.save(commentLike);
    }

    public boolean existsByQuestionCommentIdAndMemberId(final Long commentId, final Long currentMemberId) {
        return commentLikeRepository.existsByQuestionCommentIdAndMemberId(commentId, currentMemberId);
    }

    // 좋아요 취소
    @Transactional
    public void cancelLike(final QuestionComment findQuestionComment, final Member currentMember) {
        CommentLike commentLike = commentLikeRepository.findByQuestionCommentIdAndMemberId(findQuestionComment.getId(),
                currentMember.getId()).orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));
        commentLikeRepository.deletedById(commentLike.getId());
    }
}
