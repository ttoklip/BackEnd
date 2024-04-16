package com.api.ttoklip.domain.notification.repository;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.QComment;
import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationCommentRepository {

    private final JPAQueryFactory queryFactory;
    private final QComment comment = QComment.comment;
    private final QMember member = QMember.member;

    public Comment findParentCommentFetchJoin(final Long commentId) {
        Comment comment = queryFactory
                .select(this.comment)
                .from(this.comment)
                .leftJoin(member)
                .fetchJoin()
                .where(
                        this.comment.id.eq(commentId),
                        this.comment.deleted.isFalse()
                )
                .distinct()
                .fetchOne();
        return Optional.ofNullable(comment)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }
}
