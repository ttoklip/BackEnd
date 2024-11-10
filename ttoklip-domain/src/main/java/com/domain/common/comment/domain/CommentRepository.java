package com.domain.common.comment.domain;


import com.domain.honeytip.domain.HoneyTipComment;
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

    List<CommunityComment> findCommentsByCommunityId(Long postId);
}
