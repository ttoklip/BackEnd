package com.api.ttoklip.domain.newsletter.post.repository;

import static com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment.newsletterComment;

import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.image.domain.QNewsletterImage;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.QNewsletter;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NewsletterQueryDslRepositoryImpl implements NewsletterQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Newsletter findByIdFetchJoin(Long postId) {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        QNewsletterImage newsletterImage = QNewsletterImage.newsletterImage;

        Newsletter findNewsletter = queryFactory
                .selectFrom(qNewsletter)
                .leftJoin(qNewsletter.newsletterImageList, newsletterImage)
                .fetchJoin()
                .where(qNewsletter.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(findNewsletter)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    @Override
    public List<NewsletterComment> findActiveCommentsByNewsletterId(Long postId) {
        return queryFactory
                .selectFrom(newsletterComment)
                .where(
                        matchNewsletterId(postId),
                        getCommentActivate()
                )
                .orderBy(
                        newsletterComment.createdDate.asc(),
                        newsletterComment.parent.id.asc().nullsFirst()
                )
                .fetch();
    }

    @Override
    public Long findNewsletterCount() {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        return queryFactory
                .select(Wildcard.count)
                .from(qNewsletter)
                .fetchOne();
    }

    private BooleanExpression matchNewsletterId(final Long newsletterId) {
        return newsletterComment.newsletter.id.eq(newsletterId);
    }

    private BooleanExpression getCommentActivate() {
        return newsletterComment.deleted.isFalse();
    }

}
