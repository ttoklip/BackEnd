package com.api.honeytip.application;

import com.api.common.ReportWebCreate;
import com.api.global.success.Message;
import com.domain.comment.application.CommentService;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.notification.domain.annotation.SendCommentNotification;
import com.domain.notification.domain.vo.NotiCategory;
import com.domain.report.application.ReportService;
import com.domain.report.domain.ReportCreate;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipCommentFacade {

    private final HoneyTipPostService honeyTipPostService;
    private final CommentService commentService;
    private final ReportService reportService;
    private final MemberService memberService;

    @Transactional
    public Message register(final Long postId, final CommentCreate request, final Long currentMemberId) {
        HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member currentMember = memberService.getById(currentMemberId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findHoneyTip, parentComment, currentMember).getId();
            return Message.registerCommentSuccess(HoneyTipComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findHoneyTip, currentMember).getId();
        return Message.registerCommentSuccess(HoneyTipComment.class, newCommentId);
    }


    // 대댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.NEWS_LETTER_CHILD_COMMENT)
    public Comment registerCommentWithParent(final CommentCreate request, final HoneyTip findHoneyTip,
                                           final Comment parentComment, final Member member) {
        HoneyTipComment honeyTipComment = HoneyTipComment.withParentOf(request, parentComment, findHoneyTip, member);
        return commentService.register(honeyTipComment);
    }

    // 최상위 댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.HONEY_TIP_COMMENT)
    public Comment registerCommentOrphanage(final CommentCreate request, final HoneyTip findHoneyTip,
                                          final Member member) {
        HoneyTipComment honeyTipComment = HoneyTipComment.orphanageOf(request, findHoneyTip, member);
        return commentService.register(honeyTipComment);
    }

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
        return Message.deleteCommentSuccess(HoneyTipComment.class, commentId);
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}
