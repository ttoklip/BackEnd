package com.api.ttoklip.domain.honeytip.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipPostService;
import com.api.ttoklip.global.success.Message;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipCommentService {

    private final HoneyTipPostService honeyTipPostService;
    private final CommentService commentService;
    private final ReportService reportService;

    public Message register(final Long postId, final CommentCreateRequest request) {
        HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findHoneyTip, parentComment);
            return Message.registerCommentSuccess(HoneyTipComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findHoneyTip);
        return Message.registerCommentSuccess(HoneyTipComment.class, newCommentId);
    }


    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreateRequest request, final HoneyTip findHoneyTip,
                                           final Comment parentComment) {
        HoneyTipComment honeyTipComment = HoneyTipComment.withParentOf(request, parentComment, findHoneyTip);
        commentService.register(honeyTipComment);
        return honeyTipComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreateRequest request, final HoneyTip findHoneyTip) {
        HoneyTipComment honeyTipComment = HoneyTipComment.orphanageOf(request, findHoneyTip);
        commentService.register(honeyTipComment);
        return honeyTipComment.getId();
    }

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportCreateRequest request) {
        Comment comment = commentService.findComment(commentId);
        reportService.reportComment(request, comment);

        return Message.reportCommentSuccess(HoneyTipComment.class, commentId);
    }


    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long commentId) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(HoneyTipComment.class, commentId);
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */
}
