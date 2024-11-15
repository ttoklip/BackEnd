package com.domain.comment.repository;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentRepository;
import com.domain.community.domain.CommunityComment;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.newsletter.domain.NewsletterComment;
import com.domain.question.domain.QuestionComment;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeCommentRepository implements CommentRepository {

    private final Map<Long, Comment> commentMap = new HashMap<>();
    private Long id = 0L;

    private boolean isActivated(Comment comment) {
        return !comment.isDeleted();
    }

    @Override
    public Comment findByIdActivated(final Long commentId) {
        return Optional.ofNullable(commentMap.get(commentId))
                .filter(this::isActivated)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }

    @Override
    public Optional<Comment> findByIdActivatedOptional(final Long commentId) {
        return Optional.ofNullable(commentMap.get(commentId))
                .filter(this::isActivated);
    }

    @Override
    public List<HoneyTipComment> findCommentsByHoneyTipId(final Long honeyTipId) {
        return commentMap.values().stream()
                .filter(comment -> comment instanceof HoneyTipComment)
                .map(comment -> (HoneyTipComment) comment)
                .filter(comment -> comment.getHoneyTip().getId().equals(honeyTipId) && isActivated(comment))
                .sorted(Comparator.comparing(
                                (HoneyTipComment c) -> c.getParent() != null ? c.getParent().getId() : null)
                        .thenComparing(HoneyTipComment::getCreatedDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsletterComment> findCommentsByNewsletterId(final Long newsletterId) {
        return commentMap.values().stream()
                .filter(comment -> comment instanceof NewsletterComment)
                .map(comment -> (NewsletterComment) comment)
                .filter(comment -> comment.getNewsletter().getId().equals(newsletterId) && isActivated(
                        comment))  // 'newsletterId'와 'deleted' 여부 확인
                .sorted(Comparator.comparing(
                                (NewsletterComment c) -> c.getParent() != null ? c.getParent().getId() : null)
                        .thenComparing(NewsletterComment::getCreatedDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionComment> findQuestionCommentsByQuestionId(final Long questionId) {
        return commentMap.values().stream()
                .filter(comment -> comment instanceof QuestionComment)
                .map(comment -> (QuestionComment) comment)
                .filter(comment -> comment.getQuestion().getId().equals(questionId) && isActivated(comment))
                .sorted(Comparator.comparing(
                                (QuestionComment c) -> c.getParent() != null ? c.getParent().getId() : null)
                        .thenComparing(QuestionComment::getCreatedDate))
                .collect(Collectors.toList());
    }


    @Override
    public Comment save(final Comment comment) {
        id++;
        Comment saveComment = Comment.testBuilder()
                .id(id)
                .parent(comment.getParent())
                .content(comment.getContent())
                .member(comment.getMember())
                .build();

        commentMap.put(id, saveComment);
        return saveComment;
    }

    @Override
    public QuestionComment findQuestionCommentWithWriterByCommentId(final Long commentId) {
        return Optional.ofNullable(commentMap.get(commentId))
                .filter(comment -> comment instanceof QuestionComment)
                .map(comment -> (QuestionComment) comment)
                .filter(this::isActivated)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }

    @Override
    public List<CommunityComment> findCommentsByCommunityId(final Long commentid) {
        return commentMap.values().stream()
                .filter(comment -> comment instanceof CommunityComment)
                .map(comment -> (CommunityComment) comment)
                .filter(comment -> comment.getCommunity().getId().equals(commentid) && isActivated(comment))
                .sorted(Comparator.comparing(
                                (CommunityComment c) -> c.getParent() != null ? c.getParent().getId() : null)
                        .thenComparing(CommunityComment::getCreatedDate))
                .collect(Collectors.toList());
    }
}