package com.api.ttoklip.domain.town.community.facade;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.domain.town.community.domain.CommunityComment;
import com.api.ttoklip.domain.town.community.service.CommunityCommentService;
import com.api.ttoklip.domain.town.community.service.CommunityPostService;
import com.api.ttoklip.global.success.Message;
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
    public Message register(final Long postId, final CommentCreateRequest request, final Long currentMemberId) {
        Community findCommunity = communityPostService.getCommunity(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);
        Member currentMember = memberService.findById(currentMemberId);


        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            Long newCommentId = registerCommentWithParent(request, findCommunity, parentComment, currentMember);
            return Message.registerCommentSuccess(CommunityComment.class, newCommentId);
        }

        // 최상위 댓글 생성
        Long newCommentId = registerCommentOrphanage(request, findCommunity, currentMember);
        return Message.registerCommentSuccess(CommunityComment.class, newCommentId);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreateRequest request, final Community findCommunity,
                                           final Comment parentComment, final Member member) {
        CommunityComment communityComment = CommunityComment.withParentOf(request, parentComment, findCommunity, member);
        commentService.register(communityComment);
        return communityComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreateRequest request, final Community findCommunity,
                                          final Member member) {
        CommunityComment communityComment = CommunityComment.orphanageOf(request, findCommunity, member);
        commentService.register(communityComment);
        return communityComment.getId();
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long commentId, final ReportCreateRequest request) {
        Comment comment = commentService.findComment(commentId);
        reportService.reportComment(request, comment);

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
