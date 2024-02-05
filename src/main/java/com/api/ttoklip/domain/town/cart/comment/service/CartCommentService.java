package com.api.ttoklip.domain.town.cart.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.service.CartPostService;
import java.util.Optional;

import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartCommentService {

    private final CartPostService cartPostService;
    private final CommentService commentService;
    private final ReportService reportService;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final Long postId, final CommentCreateRequest request) {
        Cart findCart = cartPostService.findCart(postId);

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
    public Message delete(final Long commentId) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(CartComment.class, commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}
