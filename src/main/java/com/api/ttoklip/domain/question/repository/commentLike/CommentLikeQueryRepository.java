package com.api.ttoklip.domain.question.repository.commentLike;

import com.api.ttoklip.domain.common.comment.CommentLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.api.ttoklip.domain.common.comment.QCommentLike.commentLike;

@Repository
@RequiredArgsConstructor
public class CommentLikeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<CommentLike> findByQuestionCommentIdAndMemberId(Long commentId, Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(commentLike)
                        .where(
                                commentLike.questionComment.id.eq(commentId),
                                commentLike.member.id.eq(memberId)
                        )
                        .fetchOne()
        );
    }

    public boolean existsByQuestionCommentIdAndMemberId(Long commentId, Long memberId) {
        Long count = queryFactory
                .select(commentLike.count())
                .from(commentLike)
                .where(
                        commentLike.questionComment.id.eq(commentId),
                        commentLike.member.id.eq(memberId)
                )
                .fetchOne();

        return count != null && count > 0;
    }
}
