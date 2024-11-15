package com.domain.newsletter.application;

import com.domain.comment.domain.CommentRepository;
import com.domain.newsletter.domain.NewsletterComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterCommentService {

    private final CommentRepository commentRepository;

    public List<NewsletterComment> findCommentsByNewsletterId(final Long postId) {
        return commentRepository.findCommentsByNewsletterId(postId);
    }
}
