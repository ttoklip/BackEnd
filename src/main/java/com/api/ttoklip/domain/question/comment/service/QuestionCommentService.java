package com.api.ttoklip.domain.question.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.question.post.service.QuestionPostService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommentService {

    private final QuestionPostService questionPostService;
    private final CommentService commentService;
    private final ReportService reportService;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final Long postId, final CommentCreateRequest request) {
        Question findQuestion = questionPostService.findQuestionById(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            return registerCommentWithParent(request, findQuestion, parentComment);
        }

        // 최상위 댓글 생성
        return registerCommentOrphanage(request, findQuestion);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreateRequest request, final Question findQuestion,
                                           final Comment parentComment) {
        QuestionComment newQuestionComment = QuestionComment.withParentOf(request, parentComment, findQuestion);
        commentService.register(newQuestionComment);
        return newQuestionComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreateRequest request, final Question findQuestion) {
        QuestionComment newQuestionComment = QuestionComment.orphanageOf(request, findQuestion);
        commentService.register(newQuestionComment);
        return newQuestionComment.getId();
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public void report(final Long commentId, final ReportCreateRequest request) {
        Comment comment = commentService.findComment(commentId);
        reportService.reportComment(request, comment);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */
    @Transactional
    public void edit(final Long commentId, final CommentEditRequest request) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.edit(commentId, request);
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public void delete(final Long commentId) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.deleteById(commentId);
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */
}
