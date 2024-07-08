package com.api.ttoklip.domain.question.comment.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.question.comment.domain.QQuestionComment.questionComment;

import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuestionCommentRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

    public QuestionComment findByCommentIdFetchJoin(final Long commentId) {
        QuestionComment findQuestionComment = jpaQueryFactory
                .selectFrom(questionComment)
                .where(
                        questionComment.id.eq(commentId),
                        questionComment.deleted.isFalse()
                )
                .leftJoin(questionComment.member, member).fetchJoin()
                .fetchOne();

        return Optional.ofNullable(findQuestionComment)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }
}
