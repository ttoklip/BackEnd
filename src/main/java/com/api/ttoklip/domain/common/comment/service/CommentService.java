package com.api.ttoklip.domain.common.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.repository.CommentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void register(final Comment comment) {
        commentRepository.save(comment);
    }

    public Optional<Comment> findParentComment(final Long parentCommentId) {
        if (parentCommentId != null) {
            return commentRepository.findById(parentCommentId);
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteById(final Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
