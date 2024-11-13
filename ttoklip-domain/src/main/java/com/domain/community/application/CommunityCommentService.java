package com.domain.community.application;

import com.domain.common.comment.domain.CommentRepository;
import com.domain.community.domain.CommunityComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentService {

    private final CommentRepository commentRepository;

    public List<CommunityComment> findCommentsByCommunityId(final Long postId) {
        return commentRepository.findCommentsByCommunityId(postId);
    }
}

