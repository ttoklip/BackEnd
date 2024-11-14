package com.api.cart.application;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.common.annotation.FilterBadWord;
import com.domain.cart.application.CartPostService;
import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartComment;
import com.domain.comment.application.CommentService;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.comment.domain.CommentEdit;
import com.common.annotation.SendCommentNotification;
import com.common.NotiCategory;
import com.domain.report.domain.ReportCreate;
import com.domain.report.application.ReportService;
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
    @FilterBadWord
    public Message register(final Long postId, final CommentCreate request, final Long memberId) {
        Cart findCart = cartPostService.findByIdActivated(postId);

        // comment 부모 찾기
        Long parentCommentId = request.parentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member member = memberService.getById(memberId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findCart, parentComment, member).getId();
            return Message.registerCommentSuccess(CartComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findCart, member).getId();
        return Message.registerCommentSuccess(CartComment.class, newCommentId);
    }

    // 대댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.OUR_TOWN_CHILD_COMMENT)
    public Comment registerCommentWithParent(final CommentCreate request, final Cart findCart,
                                           final Comment parentComment, final Member member) {
        CartComment newCartComment = CartComment.withParentOf(request, parentComment, findCart, member);
        return commentService.register(newCartComment);
    }

    // 최상위 댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.OUR_TOWN_COMMENT)
    private Comment registerCommentOrphanage(final CommentCreate request, final Cart findCart, final Member member) {
        CartComment newCartComment = CartComment.orphanageOf(request, findCart, member);
        return commentService.register(newCartComment);
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
    public Message delete(final Long commentId, final Long memberId) {
        commentService.deleteById(commentId, memberId);
        return Message.deleteCommentSuccess(CartComment.class, commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}
