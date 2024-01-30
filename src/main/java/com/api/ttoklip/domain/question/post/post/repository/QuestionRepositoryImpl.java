package com.api.ttoklip.domain.question.post.post.repository;

import static com.api.ttoklip.domain.question.post.post.domain.QQuestion.question;

import com.api.ttoklip.domain.question.post.post.domain.Question;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Question findByIdUndeleted(final Long questionId) {
        Question findQuestion = jpaQueryFactory
                .selectFrom(question)
                .where(findUnDeleted(), matchId(questionId))
                .fetchOne();

        return Optional.ofNullable(findQuestion)
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUNT));
    }

    private BooleanExpression findUnDeleted() {
        return question.deleted.isFalse();
    }

    private BooleanExpression matchId(final Long questionId) {
        return question.id.eq(questionId);
    }

}
