package com.domain.newsletter.infrastructure;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterScrapQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QNewsletterScrap newsletterScrap = QNewsletterScrap.newsletterScrap;

    public Long countNewsletterScrapsByNewsletterId(final Long newsletterId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletterScrap)
                .where(newsletterScrap.newsletter.id.eq(newsletterId))
                .fetchOne();
    }
}
