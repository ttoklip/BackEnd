package com.api.honeytip.application;

import com.api.global.success.Message;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipComment;
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
    public Message register(final Long postId, final CommentCreateRequest request, final Long currentMemberId) {
        HoneyTip findHoneyTip = honeyTipPostService.getHoneytip(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member currentMember = memberService.findById(currentMemberId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findHoneyTip, parentComment, currentMember);
            return Message.registerCommentSuccess(HoneyTipComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findHoneyTip, currentMember);
        return Message.registerCommentSuccess(HoneyTipComment.class, newCommentId);
    }


    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreateRequest request, final HoneyTip findHoneyTip,
                                           final Comment parentComment, final Member member) {
        HoneyTipComment honeyTipComment = HoneyTipComment.withParentOf(request, parentComment, findHoneyTip, member);
        commentService.register(honeyTipComment);
        return honeyTipComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreateRequest request, final HoneyTip findHoneyTip,
                                          final Member member) {
        HoneyTipComment honeyTipComment = HoneyTipComment.orphanageOf(request, findHoneyTip, member);
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
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(HoneyTipComment.class, commentId);
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}
