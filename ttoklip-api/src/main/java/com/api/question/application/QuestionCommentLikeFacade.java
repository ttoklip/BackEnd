package com.api.question.application;


import com.api.global.success.Message;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.question.application.QuestionCommentLikeService;
import com.domain.question.application.QuestionPostService;
import com.domain.question.domain.QuestionComment;
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
            Member currentMember = memberService.getById(currentMemberId);
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
