package com.api.ttoklip.domain.question.post.repository;

import static com.api.ttoklip.domain.question.comment.domain.QQuestionComment.questionComment;
import static com.api.ttoklip.domain.question.image.domain.QQuestionImage.questionImage;
import static com.api.ttoklip.domain.question.post.domain.QQuestion.question;

import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Question findByIdFetchJoin(final Long questionPostId) {
        Question findQuestion = jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionImages, questionImage)
                .fetchJoin()
                .where(question.id.eq(questionPostId))
                .fetchOne();

        return Optional.ofNullable(findQuestion)
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUND));
    }

    @Override
    public List<QuestionComment> findActiveCommentsByQuestionId(final Long questionId) {
        return jpaQueryFactory
                .selectFrom(questionComment)
                .distinct()
                .where(
                        matchQuestionId(questionId),
                        getCommentActivate()
                )
                .orderBy(
                        questionComment.createdDate.asc(),
                        questionComment.parent.id.asc().nullsFirst()
                )
                .fetch();
    }

    private BooleanExpression matchQuestionId(final Long questionId) {
        return questionComment.question.id.eq(questionId);
    }

    private BooleanExpression getCommentActivate() {
        return questionComment.deleted.isFalse();
    }

}
