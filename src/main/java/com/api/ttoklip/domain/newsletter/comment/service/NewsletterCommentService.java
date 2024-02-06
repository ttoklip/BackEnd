package com.api.ttoklip.domain.newsletter.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.service.NewsletterPostService;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.global.success.Message;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterCommentService {

    private final ReportService reportService;
    private final CommentService commentService;
    private final NewsletterPostService newsletterPostService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Message register(final Long postId, final CommentCreateRequest request) {
        Newsletter newsletter = newsletterPostService.findById(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, newsletter, parentComment);
            return Message.registerCommentSuccess(NewsletterComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, newsletter);
        return Message.registerCommentSuccess(NewsletterComment.class, newCommentId);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreateRequest request, final Newsletter newsletter,
                                           final Comment parentComment) {
        NewsletterComment newsletterComment = NewsletterComment.withParentOf(request, parentComment, newsletter);
        commentService.register(newsletterComment);
        return newsletterComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreateRequest request, final Newsletter newsletter) {
        NewsletterComment newsletterComment = NewsletterComment.orphanageOf(request, newsletter);
        commentService.register(newsletterComment);
        return newsletterComment.getId();
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportCreateRequest request) {
        Comment comment = commentService.findComment(commentId);
        reportService.reportComment(request, comment);
        return Message.reportCommentSuccess(Newsletter.class, commentId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long commentId) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(Newsletter.class, commentId);
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */
}
