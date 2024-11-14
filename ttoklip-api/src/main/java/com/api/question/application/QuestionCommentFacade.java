package com.api.question.application;

import com.api.common.ReportWebCreate;
import com.api.global.success.Message;
import com.common.annotation.FilterBadWord;
import com.domain.comment.application.CommentService;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.common.annotation.SendCommentNotification;
import com.common.NotiCategory;
import com.domain.report.application.ReportService;
import com.domain.report.domain.ReportCreate;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.question.application.QuestionPostService;
import com.domain.question.domain.Question;
import com.domain.question.domain.QuestionComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommentFacade {

    private final ReportService reportService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final QuestionPostService questionPostService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    @FilterBadWord
    public Message register(final Long postId, final CommentCreate request, final Long currentMemberId) {
        Question findQuestion = questionPostService.getQuestion(postId);

        // comment 부모 찾기
        Long parentCommentId = request.parentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member currentMember = memberService.getById(currentMemberId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findQuestion, parentComment, currentMember).getId();
            return Message.registerCommentSuccess(QuestionComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findQuestion, currentMember).getId();
        return Message.registerCommentSuccess(QuestionComment.class, newCommentId);
    }

    // 대댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.QUESTION_CHILD_COMMENT)
    public Comment registerCommentWithParent(final CommentCreate create, final Question question,
                                           final Comment parentComment, final Member member) {
        QuestionComment newQuestionComment = QuestionComment.withParentOf(create, parentComment, question, member);
        return commentService.register(newQuestionComment);
    }

    // 최상위 댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.QUESTION_COMMENT)
    public Comment registerCommentOrphanage(final CommentCreate create, final Question findQuestion,
                                          final Member member) {
        QuestionComment newQuestionComment = QuestionComment.orphanageOf(create, findQuestion, member);
        return commentService.register(newQuestionComment);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportWebCreate request, final Long reporterId) {
        Comment comment = commentService.findComment(commentId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        Member reporter = memberService.getById(reporterId);
        reportService.reportComment(create, comment, reporter);
        return Message.reportCommentSuccess(HoneyTipComment.class, commentId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long commentId, final Long memberId) {
        commentService.deleteById(commentId, memberId);
        return Message.deleteCommentSuccess(QuestionComment.class, commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}

