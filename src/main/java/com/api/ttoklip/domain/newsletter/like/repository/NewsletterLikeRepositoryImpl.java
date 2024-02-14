package com.api.ttoklip.domain.newsletter.like.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.api.ttoklip.domain.newsletter.like.entity.QNewsletterLike.newsletterLike;


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
