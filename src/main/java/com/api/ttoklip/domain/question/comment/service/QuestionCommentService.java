package com.api.ttoklip.domain.question.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.like.service.CommentLikeService;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.domain.question.post.service.QuestionCommonService;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommentService {

    private final CommentService commentService;
    private final ReportService reportService;
    private final QuestionCommonService questionCommonService;
    private final CommentLikeService commentLikeService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Message register(final Long postId, final CommentCreateRequest request) {
        Question findQuestion = questionCommonService.getQuestion(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findQuestion, parentComment);
            return Message.registerCommentSuccess(QuestionComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findQuestion);
        return Message.registerCommentSuccess(QuestionComment.class, newCommentId);
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
    public Message report(final Long commentId, final ReportCreateRequest request) {
        Comment comment = commentService.findComment(commentId);
        reportService.reportComment(request, comment);

        return Message.reportCommentSuccess(QuestionComment.class, commentId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */
    /*
    @Transactional
    public Message edit(final Long commentId, final CommentEditRequest request) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.edit(commentId, request);
        return Message.editCommentSuccess(QuestionComment.class, commentId);
    }
    */
    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long commentId) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(QuestionComment.class, commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- LIKE -------------------------------------------- */
    @Transactional
    public Message registerLike(Long commentId) {
        commentLikeService.registerLike(commentId);
        return Message.likePostSuccess(Question.class, commentId);
    }

    @Transactional
    public Message cancleLike(Long commentId) {
        commentLikeService.cancelLike(commentId);
        return Message.likePostCancel(Question.class, commentId);
    }

    /* -------------------------------------------- LIKE 끝 -------------------------------------------- */

}
