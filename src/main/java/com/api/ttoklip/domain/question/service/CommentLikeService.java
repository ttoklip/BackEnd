package com.api.ttoklip.domain.question.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.common.comment.CommentLike;
import com.api.ttoklip.domain.question.repository.CommentLikeRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final QuestionPostService questionPostService;


    // 좋아요 생성
    public void registerLike(final Long commentId) {
        Long currentMemberId = getCurrentMember().getId();
        boolean exists = commentLikeRepository.existsByQuestionCommentIdAndMemberId(commentId, currentMemberId);
        if (exists) {
            return; // 이미 스크랩이 존재하면 좋아요를 생성하지 않고 return
        }

        QuestionComment findQuestionComment = questionPostService.getQuestionComment(commentId);

        CommentLike commentLike = CommentLike.from(findQuestionComment);
        commentLikeRepository.save(commentLike);
    }

    // 좋아요 취소
    @Transactional
    public void cancelLike(final Long commentId) {
        // commentId (댓글 ID)
        QuestionComment findQuestionComment = questionPostService.getQuestionComment(commentId);
        Long findQuestionCommentId = findQuestionComment.getId();
        Long currentMemberId = getCurrentMember().getId();

        CommentLike commentLike = commentLikeRepository.findByQuestionCommentIdAndMemberId(findQuestionCommentId,
                        currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByQuestionCommentIdAndMemberId 결과가 존재하므로, 현재 사용자가 좋아요를 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        commentLikeRepository.deleteById(commentLike.getId());
    }

    public boolean existsByQuestionCommentIdAndMemberId(final Long commentId) {
        Long currentMemberId = getCurrentMember().getId();
        return commentLikeRepository.existsByQuestionCommentIdAndMemberId(commentId, currentMemberId);
    }
}
