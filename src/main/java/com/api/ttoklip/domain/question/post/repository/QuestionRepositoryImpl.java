package com.api.ttoklip.domain.question.post.repository;

import static com.api.ttoklip.domain.question.comment.domain.QQuestionComment.questionComment;
import static com.api.ttoklip.domain.question.image.domain.QQuestionImage.questionImage;
import static com.api.ttoklip.domain.question.post.domain.QQuestion.question;

import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Question findByIdFetchJoin(final Long questionPostId) {
        Question findQuestion = jpaQueryFactory
                .selectFrom(question)
                .leftJoin(question.questionImages, questionImage)
                .leftJoin(question.questionComments, questionComment)
                .fetchJoin()
                .where(question.id.eq(questionPostId))
                .orderBy(
                        questionComment.question.id.asc().nullsFirst(),
                        questionComment.createdDate.asc()
                )
                .fetchOne();

        return Optional.ofNullable(findQuestion)
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUNT));
    }

}
