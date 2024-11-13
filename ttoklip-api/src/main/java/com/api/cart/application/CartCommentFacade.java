package com.api.cart.application;

import com.api.common.ReportWebCreate;
import com.api.global.success.Message;
import com.domain.cart.application.CartPostService;
import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartComment;
import com.domain.common.comment.application.CommentService;
import com.domain.common.comment.domain.Comment;
import com.domain.common.comment.domain.CommentCreate;
import com.domain.common.comment.domain.CommentEdit;
import com.domain.common.report.domain.ReportCreate;
import com.domain.common.report.application.ReportService;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartCommentFacade {

    private final CartPostService cartPostService;
    private final CommentService commentService;
    private final ReportService reportService;
    private final MemberService memberService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Message register(final Long postId, final CommentCreate request, final Long memberId) {
        Cart findCart = cartPostService.findByIdActivated(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member member = memberService.getById(memberId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findCart, parentComment, member);
            return Message.registerCommentSuccess(CartComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findCart, member);
        return Message.registerCommentSuccess(CartComment.class, newCommentId);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreate request, final Cart findCart,
                                           final Comment parentComment, final Member member) {
        CartComment newCartComment = CartComment.withParentOf(request, parentComment, findCart, member);
        commentService.register(newCartComment);
        return newCartComment.getId();
    }
    // 최상위 댓글 생성

    private Long registerCommentOrphanage(final CommentCreate request, final Cart findCart, final Member member) {
        CartComment newCartComment = CartComment.orphanageOf(request, findCart, member);
        commentService.register(newCartComment);
        return newCartComment.getId();
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportWebCreate request, final Long reporterId) {
        Comment comment = commentService.findComment(commentId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        Member reporter = memberService.getById(reporterId);
        reportService.reportComment(create, comment, reporter);
        return Message.reportCommentSuccess(CartComment.class, commentId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */

    @Transactional
    public void edit(final Long commentId, final CommentEdit request) {
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
