package com.api.ttoklip.domain.common.comment.repository;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import com.api.ttoklip.domain.town.community.domain.CommunityComment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment findByIdActivated(Long commentId);

    Optional<Comment> findByIdActivatedOptional(Long commentId);

    List<HoneyTipComment> findCommentsByHoneyTipId(Long honeyTipId);

    List<NewsletterComment> findCommentsByNewsletterId(Long newsletterId);

    List<QuestionComment> findQuestionCommentsByQuestionId(Long questionId);

    void save(Comment comment);

    QuestionComment findQuestionCommentWithWriterByCommentId(Long commentId);
}
