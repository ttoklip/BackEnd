package com.api.ttoklip.domain.town.community.service;

import com.api.ttoklip.domain.common.comment.repository.CommentRepository;
import com.api.ttoklip.domain.town.community.domain.CommunityComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentService {

    private final CommentRepository commentRepository;

    public List<CommunityComment> findCommentsByCommunityId(final Long postId) {
        return commentRepository.findCommentsByCommunityId(postId);
    }
}

