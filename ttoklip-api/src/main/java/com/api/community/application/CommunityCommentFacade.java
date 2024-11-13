package com.api.community.application;

import com.api.common.ReportWebCreate;
import com.api.global.success.Message;
import com.domain.common.comment.application.CommentService;
import com.domain.common.comment.domain.Comment;
import com.domain.common.comment.domain.CommentCreate;
import com.domain.common.report.domain.ReportCreate;
import com.domain.common.report.application.ReportService;
import com.domain.community.application.CommunityCommentService;
import com.domain.community.application.CommunityPostService;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityComment;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentFacade {

    private final CommunityPostService communityPostService;
    private final CommentService commentService;
    private final ReportService reportService;
    private final CommunityCommentService communityCommentService;

    private final MemberService memberService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Message register(final Long postId, final CommentCreate request, final Long currentMemberId) {
        Community findCommunity = communityPostService.getCommunity(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member member = memberService.getById(currentMemberId);



        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findCommunity, parentComment, member);
            return Message.registerCommentSuccess(CommunityComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findCommunity, member);
        return Message.registerCommentSuccess(CommunityComment.class, newCommentId);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreate request, final Community findCommunity,
                                           final Comment parentComment, final Member member) {
        CommunityComment communityComment = CommunityComment.withParentOf(request, parentComment, findCommunity, member);
        commentService.register(communityComment);
        return communityComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreate request, final Community findCommunity,
                                          final Member member) {
        CommunityComment communityComment = CommunityComment.orphanageOf(request, findCommunity, member);
        commentService.register(communityComment);
        return communityComment.getId();
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
    public void edit(final Long commentId, final CommentEditRequest request) {
        commentService.edit(commentId, request);
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */

    @Transactional
    public Message delete(final Long commentId) {
        commentService.deleteById(commentId);
        return Message.deleteCommentSuccess(CommunityComment.class, commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */
}
