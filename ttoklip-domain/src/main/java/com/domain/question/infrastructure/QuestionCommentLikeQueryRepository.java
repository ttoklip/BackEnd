/*
package com.domain.question.infrastructure;

import com.domain.comment.domain.CommentLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionCommentLikeQueryRepository {

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


 */