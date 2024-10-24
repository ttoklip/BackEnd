package com.api.ttoklip.domain.common.comment.repository;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import java.util.List;
import java.util.Optional;

import com.api.ttoklip.domain.question.domain.QuestionComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentQueryRepository commentQueryRepository;

    @Override
    public Comment findByIdActivated(final Long commentId) {
        return commentQueryRepository.findByIdActivated(commentId);
    }

    @Override
    public Optional<Comment> findByIdActivatedOptional(final Long commentId) {
        return commentQueryRepository.findByIdActivatedOptional(commentId);
    }

    @Override
    public List<HoneyTipComment> findCommentsByHoneyTipId(final Long honeyTipId) {
        return commentQueryRepository.findCommentsByHoneyTipId(honeyTipId);
    }

    @Override
    public List<NewsletterComment> findCommentsByNewsletterId(final Long newsletterId) {
        return commentQueryRepository.findCommentsByNewsletterId(newsletterId);
    }

    @Override
    public List<QuestionComment> findCommentsByQuestionId(final Long questionId) {
        return commentQueryRepository.findCommentsByQuestionId(questionId);
    }

    @Override
    public void save(final Comment comment) {
        commentJpaRepository.save(comment);
    }
}
