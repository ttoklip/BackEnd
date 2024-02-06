package com.api.ttoklip.domain.newsletter.post.domain.repository;

import static com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment.newsletterComment;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.image.domain.QNewsletterImage;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.QNewsletter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
    public List<Newsletter> findLatestNewslettersByCategory(Category category, int limit) {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        return queryFactory.selectFrom(qNewsletter)
                .where(qNewsletter.category.eq(category))
                .orderBy(qNewsletter.createdDate.desc())
                .limit(limit)
                .fetch();
    }

    // To do. 하루 한번만 랜덤으로 뽑히도록 리팩토링
    @Override
    public List<Newsletter> findRandomNewslettersByCategory(Category category, int limit) {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        return queryFactory.selectFrom(qNewsletter)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(limit)
                .fetch();
    }

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
                .orElseThrow(RuntimeException::new);
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

    private BooleanExpression matchNewsletterId(final Long newsletterId) {
        return newsletterComment.newsletter.id.eq(newsletterId);
    }

    private BooleanExpression getCommentActivate() {
        return newsletterComment.deleted.isFalse();
    }

}
