package com.api.ttoklip.domain.question.comment.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.privacy.domain.QProfile.profile;
import static com.api.ttoklip.domain.question.comment.domain.QQuestionComment.questionComment;
import static com.api.ttoklip.domain.question.post.domain.QQuestion.question;

import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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

    public List<QuestionComment> findQuestionCommentsByQuestionId(final Long questionId) {
        return jpaQueryFactory
                .select(questionComment)
                .from(questionComment)
                .leftJoin(questionComment.question, question).fetchJoin()
                .leftJoin(questionComment.member, member).fetchJoin()
                .leftJoin(questionComment.member.profile, profile).fetchJoin()
                .where(
                        questionComment.question.id.eq(questionId),
                        questionComment.question.deleted.isFalse()
                )
                .fetch();
    }
}
