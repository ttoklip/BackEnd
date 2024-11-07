package com.api.ttoklip.domain.town.cart.facade;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.domain.town.cart.domain.CartComment;
import com.api.ttoklip.domain.town.cart.service.CartPostService;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartCommentFacade {

    private final CartPostService cartPostService;
    private final CommentService commentService;
    private final ReportService reportService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Message register(final Long postId, final CommentCreateRequest request) {
        Cart findCart = cartPostService.findByIdActivated(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findCart, parentComment);
            return Message.registerCommentSuccess(CartComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findCart);
        return Message.registerCommentSuccess(CartComment.class, newCommentId);
    }
    // 대댓글 생성

    private Long registerCommentWithParent(final CommentCreateRequest request, final Cart findCart,
                                           final Comment parentComment) {
        CartComment newCartComment = CartComment.withParentOf(request, parentComment, findCart);
        commentService.register(newCartComment);
        return newCartComment.getId();
    }
    // 최상위 댓글 생성

    private Long registerCommentOrphanage(final CommentCreateRequest request, final Cart findCart) {
        CartComment newCartComment = CartComment.orphanageOf(request, findCart);
        commentService.register(newCartComment);
        return newCartComment.getId();
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportCreateRequest request) {
        Comment comment = commentService.findComment(commentId);
        reportService.reportComment(request, comment);

        return Message.reportCommentSuccess(CartComment.class, commentId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */

    @Transactional
    public void edit(final Long commentId, final CommentEditRequest request) {
        commentService.edit(commentId, request);
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */

    @Transactional
    public Message delete(final Long commentId) {
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(CartComment.class, commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}
