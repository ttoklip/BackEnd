package com.api.ttoklip.domain.question.facade;

import com.api.ttoklip.domain.aop.notification.annotation.SendNotification;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.service.QuestionCommentLikeService;
import com.api.ttoklip.domain.question.service.QuestionPostService;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommentLikeFacade {

    private final MemberService memberService;
    private final QuestionPostService questionPostService;
    private final QuestionCommentLikeService questionCommentLikeService;

    /* -------------------------------------------- LIKE -------------------------------------------- */

    @Transactional
    @SendNotification
    public Message registerLike(final Long commentId, final Long currentMemberId) {
        boolean exists = questionCommentLikeService.isCommentLikeExists(commentId, currentMemberId);
        if (!exists) {
            QuestionComment findQuestionComment = questionPostService.getQuestionComment(commentId);
            Member currentMember = memberService.findById(currentMemberId);
            questionCommentLikeService.registerLike(findQuestionComment, currentMember);
        }
        return Message.likePostSuccess(QuestionComment.class, commentId);
    }

    @Transactional
    public Message cancelLike(final Long commentId, final Long currentMemberId) {
        QuestionComment findQuestionComment = questionPostService.getQuestionComment(commentId);
        questionCommentLikeService.cancelLike(findQuestionComment, currentMemberId);
        return Message.likePostCancel(QuestionComment.class, commentId);
    }

    /* -------------------------------------------- LIKE 끝 -------------------------------------------- */

}
