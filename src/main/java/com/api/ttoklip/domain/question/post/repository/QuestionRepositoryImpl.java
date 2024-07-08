package com.api.ttoklip.domain.question.post.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.question.comment.domain.QQuestionComment.questionComment;
import static com.api.ttoklip.domain.question.image.domain.QQuestionImage.questionImage;
import static com.api.ttoklip.domain.question.post.domain.QQuestion.question;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public QuestionComment findByCommentIdActivated(final Long commentId) {
        QuestionComment findQuestionComment = jpaQueryFactory
                .selectFrom(questionComment)
                .distinct()
                .leftJoin(questionComment.member, member).fetchJoin()
                .where(
                        matchCommentId(commentId)
                )
//                .fetchFirst();
                .fetchOne();
        return Optional.ofNullable(findQuestionComment)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }

    private BooleanExpression matchCommentId(final Long commentId) {
        return questionComment.id.eq(commentId);
    }

    @Override
    public Question findByIdActivated(final Long questionId) {
        Question findQuestion = jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.member, member).fetchJoin()
                .where(
                        matchId(questionId), getQuestionActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findQuestion)
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long questionId) {
        return question.id.eq(questionId);
    }

    private BooleanExpression getQuestionActivate() {
        return question.deleted.isFalse();
    }

    @Override
    public Question findByIdFetchJoin(final Long questionPostId) {
        Question findQuestion = jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionImages, questionImage)
                .leftJoin(question.member, member).fetchJoin()
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
                        matchQuestionId(questionId)
                )
                .orderBy(
                        questionComment.parent.id.asc().nullsFirst(),
                        questionComment.createdDate.asc()
                )
                .fetch();
    }

    private BooleanExpression matchQuestionId(final Long questionId) {
        return questionComment.question.id.eq(questionId);
    }

    // ------------------------------------ 메인 페이지 꿀팁공유해요 카테고리별 페이징 조회 ------------------------------------

    @Override
    public Page<Question> matchCategoryPaging(final Category category, final Pageable pageable) {
        List<Question> pageContent = getPageContent(category, pageable);
        Long count = pagingCountQuery(category);
        return new PageImpl<>(pageContent, pageable, count);
    }

    private List<Question> getPageContent(final Category category, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .where(
                        matchCategory(category)
                )
                .leftJoin(question.questionComments, questionComment)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(question.id.desc())
                .fetch();
    }

    private BooleanExpression matchCategory(final Category category) {
        return question.category.eq(category);
    }

    private Long pagingCountQuery(final Category category) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(question)
                .where(
                        matchCategory(category)
                )
                .fetchOne();
    }

    // ------------------------------------ 메인 페이지 꿀팁공유해요 카테고리별 페이징 조회 끝 ------------------------------------

}
