package com.api.ttoklip.domain.newsletter.repository;

import static com.api.ttoklip.domain.newsletter.domain.QNewsletterLike.newsletterLike;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterLikeRepositoryImpl implements NewsletterLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countNewsletterLikesByNewsletterId(final Long newsletterId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletterLike)
                .where(newsletterLike.newsletter.id.eq(newsletterId))
                .fetchOne();
    }

}
