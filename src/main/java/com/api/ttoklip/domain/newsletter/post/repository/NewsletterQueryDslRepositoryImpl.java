package com.api.ttoklip.domain.newsletter.post.repository;

import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.QNewsletter;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment.newsletterComment;
import static com.api.ttoklip.domain.newsletter.image.domain.QNewsletterImage.newsletterImage;
import static com.api.ttoklip.domain.newsletter.post.domain.QNewsletter.newsletter;

@RequiredArgsConstructor
public class NewsletterQueryDslRepositoryImpl implements NewsletterQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Newsletter findByIdActivated(final Long newsletterId) {
        Newsletter findNewsletter = jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.member, member)
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .fetchJoin()
                .where(
                        matchNewsletterId(newsletterId), getNewsletterActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findNewsletter)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    private BooleanExpression matchNewsletterId(final Long newsletterId) {
        return newsletter.id.eq(newsletterId);
    }

    private BooleanExpression getNewsletterActivate() {
        return newsletter.deleted.isFalse();
    }

    @Override
    public Newsletter findByIdFetchJoin(Long newsletterPostId) {
//        QNewsletterImage newsletterImage = QNewsletterImage.newsletterImage;
        Newsletter findNewsletter = jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
//                .leftJoin(QNewsletterImage.newsletterImage, newsletterImage)
                .leftJoin(newsletter.newsletterImages, newsletterImage)
                .leftJoin(newsletter.member, member)
                .fetchJoin()
                .where(
                        getNewsletterActivate(),
                        newsletter.id.eq(newsletterPostId)
                )
                .fetchOne();

        return Optional.ofNullable(findNewsletter)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    @Override
    public List<NewsletterComment> findActiveCommentsByNewsletterId(Long newsletterId) {
        return jpaQueryFactory
                .selectFrom(newsletterComment)
                .distinct()
                .where(
                        matchNewsletterId(newsletterId)
                )
                .orderBy(
                        newsletterComment.parent.id.asc().nullsFirst(),
                        newsletterComment.createdDate.asc()
                )
                .fetch();
    }

    @Override
    public Long findNewsletterCount() {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(qNewsletter)
                .distinct()
                .fetchOne();
    }

}
