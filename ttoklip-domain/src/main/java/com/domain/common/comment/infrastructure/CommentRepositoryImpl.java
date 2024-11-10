package com.domain.common.comment.infrastructure;

import com.domain.common.comment.domain.Comment;
import com.domain.common.comment.domain.CommentRepository;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.newsletter.domain.NewsletterComment;
import java.util.List;
import java.util.Optional;
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
    public List<CommunityComment> findCommentsByCommunityId(final Long communityId) {
        return commentQueryRepository.findCommentsByCommunityId(communityId);
    }

    @Override
    public List<QuestionComment> findQuestionCommentsByQuestionId(final Long questionId) {
        return commentQueryRepository.findCommentsByQuestionId(questionId);
    }

    @Override
    public void save(final Comment comment) {
        commentJpaRepository.save(comment);
    }

    @Override
    public QuestionComment findQuestionCommentWithWriterByCommentId(final Long commentId) {
        return commentQueryRepository.findQuestionCommentWithWriterByCommentId(commentId);
    }
}
