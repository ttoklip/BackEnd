package com.domain.comment.domain;


import com.domain.community.domain.CommunityComment;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.newsletter.domain.NewsletterComment;
import com.domain.question.domain.QuestionComment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment findByIdActivated(Long commentId);

    Optional<Comment> findByIdActivatedOptional(Long commentId);

    List<HoneyTipComment> findCommentsByHoneyTipId(Long honeyTipId);

    List<NewsletterComment> findCommentsByNewsletterId(Long newsletterId);

    List<QuestionComment> findQuestionCommentsByQuestionId(Long questionId);

    Comment save(Comment comment);

    QuestionComment findQuestionCommentWithWriterByCommentId(Long commentId);

    List<CommunityComment> findCommentsByCommunityId(Long postId);
}
