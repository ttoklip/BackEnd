package com.api.community.application;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.common.annotation.FilterBadWord;
import com.domain.comment.application.CommentService;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.comment.domain.CommentEdit;
import com.domain.community.application.CommunityPostService;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityComment;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.report.application.ReportService;
import com.domain.report.domain.ReportCreate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentFacade {

    private final CommunityPostService communityPostService;
    private final CommentService commentService;
    private final ReportService reportService;
    private final MemberService memberService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    @FilterBadWord
    public Message register(final Long postId, final CommentCreate request, final Long currentMemberId) {
        Community findCommunity = communityPostService.getCommunity(postId);

        // comment 부모 찾기
        Long parentCommentId = request.parentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member member = memberService.getById(currentMemberId);



        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findCommunity, parentComment, member).getId();
            return Message.registerCommentSuccess(CommunityComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findCommunity, member).getId();
        return Message.registerCommentSuccess(CommunityComment.class, newCommentId);
    }

    // 대댓글 생성
    private Comment registerCommentWithParent(final CommentCreate request, final Community findCommunity,
                                           final Comment parentComment, final Member member) {
        CommunityComment communityComment = CommunityComment.withParentOf(request, parentComment, findCommunity, member);
        return commentService.register(communityComment);
    }

    // 최상위 댓글 생성
    private Comment registerCommentOrphanage(final CommentCreate request, final Community findCommunity,
                                          final Member member) {
        CommunityComment communityComment = CommunityComment.orphanageOf(request, findCommunity, member);
        return commentService.register(communityComment);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportWebCreate request, final Long reporterId){
        Comment comment = commentService.findComment(commentId);
        Member reporter = memberService.getById(reporterId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        reportService.reportComment(create, comment, reporter);
        return Message.reportCommentSuccess(CommunityComment.class, commentId);
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
        return Message.deleteCommentSuccess(CommunityComment.class, commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */
}
