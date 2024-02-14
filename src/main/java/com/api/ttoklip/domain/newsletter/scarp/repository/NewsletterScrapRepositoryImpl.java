package com.api.ttoklip.domain.newsletter.scarp.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.api.ttoklip.domain.newsletter.scarp.entity.QNewsletterScrap.newsletterScrap;


@RequiredArgsConstructor
public class NewsletterScrapRepositoryImpl implements NewsletterScrapRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countNewsletterScrapsByNewsletterId(final Long newsletterId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletterScrap)
                .where(newsletterScrap.newsletter.id.eq(newsletterId))
                .fetchOne();
    }


}
