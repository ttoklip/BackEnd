package com.api.ttoklip.domain.newsletter.repository;

import com.api.ttoklip.domain.newsletter.domain.QNewsletterScrap;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class NewsletterScrapRepositoryImpl implements NewsletterScrapRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QNewsletterScrap newsletterScrap = QNewsletterScrap.newsletterScrap;

    @Override
    public Long countNewsletterScrapsByNewsletterId(final Long newsletterId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletterScrap)
                .where(newsletterScrap.newsletter.id.eq(newsletterId))
                .fetchOne();
    }
}
