package com.api.ttoklip.domain.town.community.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.comment.dto.request.CommunityCommentCreateRequest;
import com.api.ttoklip.domain.town.community.comment.dto.request.CommunityCommentUpdateRequest;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentService {

    private final CommunityPostService communityPostService;
    private final CommentService commentService;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final Long postId, final CommentCreateRequest request) {
        Community findCommunity = communityPostService.findCommunityById(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            return registerCommentWithParent(request, findCommunity, parentComment);
        }

        // 최상위 댓글 생성
        return registerCommentOrphanage(request, findCommunity);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreateRequest request, final Community findCommunity,
                                           final Comment parentComment) {
        CommunityComment newCommunityComment = CommunityComment.withParentOf(request, parentComment, findCommunity);
        commentService.register(newCommunityComment);
        return newCommunityComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreateRequest request, final Community findCommunity) {
        CommunityComment newCommunityComment = CommunityComment.orphanageOf(request, findCommunity);
        commentService.register(newCommunityComment);
        return newCommunityComment.getId();
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    public static void updateCommComment(final Long commentID, final CommunityCommentUpdateRequest commCommentUpdateRequest) {
    }


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public void delete(final Long commentId) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.deleteById(commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}

