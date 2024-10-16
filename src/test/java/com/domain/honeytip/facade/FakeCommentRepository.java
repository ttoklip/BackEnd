package com.domain.honeytip.facade;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.repository.CommentRepository;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
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
    public void save(final Comment comment) {
        id++;
        Comment saveComment = Comment.testBuilder()
                .id(id)
                .parent(comment.getParent())
                .content(comment.getContent())
                .member(comment.getMember())
                .build();

        commentMap.put(id, saveComment);
    }
}
