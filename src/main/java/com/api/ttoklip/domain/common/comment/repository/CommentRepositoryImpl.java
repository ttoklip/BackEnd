package com.api.ttoklip.domain.common.comment.repository;

import static com.api.ttoklip.domain.common.comment.QComment.comment;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Comment findByIdActivated(final Long commentId) {
        Comment findComment = jpaQueryFactory
                .selectFrom(comment)
                .distinct()
                .where(
                        matchId(commentId), getCommentActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findComment)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }

    @Override
    public Optional<Comment> findByIdActivatedOptional(final Long commentId) {
        Comment findComment = jpaQueryFactory
                .selectFrom(comment)
                .distinct()
                .where(
                        matchId(commentId), getCommentActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findComment);
    }

    private BooleanExpression matchId(final Long commentId) {
        return comment.id.eq(commentId);
    }

    private BooleanExpression getCommentActivate() {
        return comment.deleted.isFalse();
    }
}
