package com.api.ttoklip.domain.common.comment.repository;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.QComment;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.domain.QHoneyTipComment;
import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.domain.QNewsletterComment;
import com.api.ttoklip.domain.question.domain.QQuestionComment;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.town.community.domain.CommunityComment;
import com.api.ttoklip.domain.town.community.domain.QCommunityComment;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;
    private final QNewsletterComment newsletterComment = QNewsletterComment.newsletterComment;
    private final QCommunityComment communityComment = QCommunityComment.communityComment;
    private final QQuestionComment questionComment = QQuestionComment.questionComment;

    private final QComment comment = QComment.comment;
    private final QMember member = QMember.member;

    public Comment findByIdActivated(final Long commentId) {
        Comment findComment = jpaQueryFactory
                .selectFrom(comment)
                .where(
                        matchId(commentId)
                )
                .fetchOne();
        return Optional.ofNullable(findComment)
                .orElseThrow(() -> new ApiException(ErrorType.COMMENT_NOT_FOUND));
    }

    public Optional<Comment> findByIdActivatedOptional(final Long commentId) {
        Comment findComment = jpaQueryFactory
                .selectFrom(comment)
                .where(
                        matchId(commentId)
                )
                .fetchOne();
        return Optional.ofNullable(findComment);
    }

    private BooleanExpression matchId(final Long commentId) {
        return comment.id.eq(commentId);
    }

    public List<HoneyTipComment> findCommentsByHoneyTipId(final Long honeyTipId) {
        return jpaQueryFactory
                .selectFrom(honeyTipComment)
                .distinct()
                .leftJoin(honeyTipComment.member, member).fetchJoin()
                .where(
                        matchHoneyTipId(honeyTipId)
                )
                .orderBy(
                        honeyTipComment.parent.id.asc().nullsFirst(),
                        honeyTipComment.createdDate.asc()
                )
                .fetch();
    }

    private BooleanExpression matchHoneyTipId(final Long honeyTipId) {
        return honeyTipComment.honeyTip.id.eq(honeyTipId);
    }

    public List<NewsletterComment> findCommentsByNewsletterId(Long newsletterId) {
        return jpaQueryFactory
                .selectFrom(newsletterComment)
                .distinct()
                .leftJoin(newsletterComment.member, member).fetchJoin()
                .where(
                        matchNewsletterId(newsletterId)
                    // 댓글은 삭제되어도 "삭제된 댓글입니다" 로 보여야하기 떄문에 데이터는 보여주도록 설정
                    // getActivatedNewsletterFromComments()
                )
                .orderBy(
                        newsletterComment.parent.id.asc().nullsFirst(),
                        newsletterComment.createdDate.asc()
                )
                .fetch();
    }

    private BooleanExpression matchNewsletterId(final Long newsletterId) {
        return newsletterComment.newsletter.id.eq(newsletterId);
    }

    public List<CommunityComment> findCommentsByCommunityId(Long communityId) {
        return jpaQueryFactory
                .selectFrom(communityComment)
                .distinct()
                .leftJoin(communityComment.member, member).fetchJoin()
                .where(
                        matchCommunityId(communityId)
                )
                .orderBy(
                        communityComment.parent.id.asc().nullsFirst(),
                        communityComment.createdDate.asc()
                )
                .fetch();
    }

    private BooleanExpression matchCommunityId(final Long communityId) {
        return communityComment.community.id.eq(communityId);
    }

    public List<QuestionComment> findCommentsByQuestionId(final Long questionId) {
        return jpaQueryFactory
                .selectFrom(questionComment)
                .distinct()
                .leftJoin(questionComment.member, member).fetchJoin()
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

    public QuestionComment findQuestionCommentWithWriterByCommentId(final Long commentId) {
        return jpaQueryFactory
                .selectFrom(questionComment)
                .distinct()
                .leftJoin(questionComment.member, member).fetchJoin()
                .where(
                        questionComment.id.eq(commentId)
                )
                .fetchOne();
    }
}
