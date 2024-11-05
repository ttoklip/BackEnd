package com.api.ttoklip.domain.question.facade;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.service.QuestionPostService;
import com.api.ttoklip.global.success.Message;
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
    public Message register(final Long postId, final CommentCreateRequest request, final Long currentMemberId) {
        Question findQuestion = questionPostService.getQuestion(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member currentMember = memberService.findById(currentMemberId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findQuestion, parentComment, currentMember);
            return Message.registerCommentSuccess(QuestionComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findQuestion, currentMember);
        return Message.registerCommentSuccess(QuestionComment.class, newCommentId);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreateRequest request, final Question findQuestion,
                                           final Comment parentComment, final Member member) {
        QuestionComment newQuestionComment = QuestionComment.withParentOf(request, parentComment, findQuestion, member);
        commentService.register(newQuestionComment);
        return newQuestionComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreateRequest request, final Question findQuestion,
                                          final Member member) {
        QuestionComment newQuestionComment = QuestionComment.orphanageOf(request, findQuestion, member);
        commentService.register(newQuestionComment);
        return newQuestionComment.getId();
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportCreateRequest request) {
        Comment comment = commentService.findComment(commentId);
        reportService.reportComment(request, comment);

        return Message.reportCommentSuccess(QuestionComment.class, commentId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long commentId) {
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(QuestionComment.class, commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}

