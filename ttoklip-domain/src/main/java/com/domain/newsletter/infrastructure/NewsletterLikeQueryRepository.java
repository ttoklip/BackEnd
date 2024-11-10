package com.domain.newsletter.infrastructure;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterLikeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Long countNewsletterLikesByNewsletterId(final Long newsletterId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletterLike)
                .where(newsletterLike.newsletter.id.eq(newsletterId))
                .fetchOne();
    }

}
