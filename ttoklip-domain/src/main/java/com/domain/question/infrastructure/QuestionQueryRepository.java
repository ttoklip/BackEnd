/*
package com.domain.question.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.Category;
import com.domain.question.domain.Question;
import com.domain.question.domain.QuestionComment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QQuestionComment questionComment = QQuestionComment.questionComment;
    private final QQuestion question = QQuestion.question;
    private final QQuestionImage questionImage = QQuestionImage.questionImage;
    private final QProfile profile = QProfile.profile;

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

    public Question findByIdFetchJoin(final Long questionPostId) {
        Question findQuestion = jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.questionImages, questionImage)
                .leftJoin(question.member, member).fetchJoin()
                .leftJoin(profile, profile).fetchJoin()
                .where(question.id.eq(questionPostId))
                .fetchOne();

        return Optional.ofNullable(findQuestion)
                .orElseThrow(() -> new ApiException(ErrorType.QUESTION_NOT_FOUND));
    }

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

    public List<Question> getHouseWork() {
        return jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.member, member).fetchJoin()
                .leftJoin(profile, profile).fetchJoin()
                .where(question.category.eq(Category.HOUSEWORK))
                .limit(10)
                .orderBy(question.id.desc())
                .fetch();
    }

    public List<Question> getRecipe() {
        return jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.member, member).fetchJoin()
                .leftJoin(profile, profile).fetchJoin()
                .where(question.category.eq(Category.RECIPE))
                .limit(10)
                .orderBy(question.id.desc())
                .fetch();
    }

    public List<Question> getSafeLiving() {
        return jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.member, member).fetchJoin()
                .leftJoin(profile, profile).fetchJoin()
                .where(question.category.eq(Category.SAFE_LIVING))
                .limit(10)
                .orderBy(question.id.desc())
                .fetch();
    }

    public List<Question> getWelfarePolicy() {
        return jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .leftJoin(question.member, member).fetchJoin()
                .leftJoin(profile, profile).fetchJoin()
                .where(question.category.eq(Category.WELFARE_POLICY))
                .limit(10)
                .orderBy(question.id.desc())
                .fetch();
    }

    public Page<Question> matchWriterPaging(final Long userId, final Pageable pageable) {
        List<Question> content = matchWriter(userId, pageable);
        Long count = writerCount();
        return new PageImpl<>(content, pageable, count);
    }

    private List<Question> matchWriter(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.questionComments, questionComment)
                .where(
                        question.member.id.eq(userId),
                        question.deleted.eq(false)
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(question.id.desc())
                .fetch();
    }

    private Long writerCount() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(question)
                .fetchOne();
    }
}

 */