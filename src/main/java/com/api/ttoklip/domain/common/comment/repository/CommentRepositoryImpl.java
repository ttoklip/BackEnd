package com.api.ttoklip.domain.common.comment.repository;

import static com.api.ttoklip.domain.common.comment.QComment.comment;
import static com.api.ttoklip.domain.member.domain.QMember.member;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.domain.QHoneyTipComment;
import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;
    private final QNewsletterComment newsletterComment = QNewsletterComment.newsletterComment;
    private final QMember member = QMember.member;

    @Override
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

    @Override
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
//                        ,getActivatedHoneyTipFromComments()
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

    private BooleanExpression getActivatedHoneyTipFromComments() {
        // todo 아래가 되어야하는 거 아닌가..? 추후 테스트하기
        // honeyTipComment.deleted.isFalse()
        return honeyTipComment.honeyTip.deleted.isFalse();
    }

    public List<NewsletterComment> findCommentsByNewsletterId(Long newsletterId) {
        return jpaQueryFactory
                .selectFrom(newsletterComment)
                .distinct()
                .leftJoin(newsletterComment.member, member).fetchJoin()
                .where(
                        matchNewsletterId(newsletterId),
                        getActivatedNewsletterFromComments()
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

    private BooleanExpression getActivatedNewsletterFromComments() {
        // todo 아래가 되어야하는 거 아닌가..? 추후 테스트하기
        // newsletterComment.deleted.isFalse()
        return newsletterComment.newsletter.deleted.isFalse();
    }

}
