package com.api.newsletter.application;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.common.annotation.FilterBadWord;
import com.domain.comment.application.CommentService;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.newsletter.application.NewsletterCommentService;
import com.domain.newsletter.application.NewsletterPostService;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterComment;
import com.domain.report.application.ReportService;
import com.domain.report.domain.ReportCreate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterCommentFacade {

    private final ReportService reportService;
    private final CommentService commentService;
    private final NewsletterPostService newsletterPostService;
    private final MemberService memberService;
    private final NewsletterCommentService newsletterCommentService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    @FilterBadWord
    public Message register(final Long postId, final CommentCreate request, final Long currentMemberId) {
        Newsletter findNewsletter = newsletterPostService.getNewsletter(postId);

        // comment 부모 찾기
        Long parentCommentId = request.parentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member currentMember = memberService.getById(currentMemberId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = newsletterCommentService.registerCommentWithParent(request, findNewsletter,
                    parentComment, currentMember).getId();
            return Message.registerCommentSuccess(NewsletterComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = newsletterCommentService.registerCommentOrphanage(request, findNewsletter, currentMember)
                .getId();
        return Message.registerCommentSuccess(NewsletterComment.class, newCommentId);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportWebCreate request, final Long reporterId) {
        Comment comment = commentService.findComment(commentId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        Member reporter = memberService.getById(reporterId);
        reportService.reportComment(create, comment, reporter);
        return Message.reportCommentSuccess(Newsletter.class, commentId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long commentId, final Long memberId) {
        commentService.deleteById(commentId, memberId);
        return Message.deleteCommentSuccess(Newsletter.class, commentId);
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */
}
