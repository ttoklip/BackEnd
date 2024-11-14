package com.domain.comment.infrastructure;

import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentRepository;
import com.domain.community.domain.CommunityComment;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.newsletter.domain.NewsletterComment;
import com.domain.question.domain.QuestionComment;
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
    public Comment save(final Comment comment) {
        return commentJpaRepository.saveAndFlush(comment);
    }

    @Override
    public QuestionComment findQuestionCommentWithWriterByCommentId(final Long commentId) {
        return commentQueryRepository.findQuestionCommentWithWriterByCommentId(commentId);
    }
}
