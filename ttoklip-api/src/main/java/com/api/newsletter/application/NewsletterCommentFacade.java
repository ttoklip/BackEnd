package com.api.newsletter.application;

import com.api.global.success.Message;
import com.domain.common.comment.application.CommentService;
import com.domain.common.comment.domain.Comment;
import com.domain.common.comment.domain.CommentCreate;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.newsletter.application.NewsletterPostService;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterComment;
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

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Message register(final Long postId, final CommentCreate request, final Long currentMemberId) {
        Newsletter findNewsletter = newsletterPostService.getNewsletter(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member currentMember = memberService.getById(currentMemberId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findNewsletter, parentComment, currentMember);
            return Message.registerCommentSuccess(NewsletterComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findNewsletter, currentMember);
        return Message.registerCommentSuccess(NewsletterComment.class, newCommentId);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreate request, final Newsletter newsletter,
                                           final Comment parentComment, final Member currentMember) {
        NewsletterComment newsletterComment = NewsletterComment.withParentOf(request, parentComment, newsletter, currentMember);
        commentService.register(newsletterComment);
        return newsletterComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreate request, final Newsletter newsletter,
                                          final Member currentMember) {
        NewsletterComment newsletterComment = NewsletterComment.orphanageOf(request, newsletter, currentMember);
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
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(Newsletter.class, commentId);
    }
    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */
}
