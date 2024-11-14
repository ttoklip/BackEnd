package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.QNewsletter;
import com.domain.newsletter.domain.QNewsletterComment;
import com.domain.newsletter.domain.QNewsletterScrap;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterScrapQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QNewsletterScrap newsletterScrap = QNewsletterScrap.newsletterScrap;
    private final QNewsletter newsletter = QNewsletter.newsletter;
    private final QNewsletterComment newsletterComment = QNewsletterComment.newsletterComment;

    public Long countNewsletterScrapsByNewsletterId(final Long newsletterId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletterScrap)
                .where(newsletterScrap.newsletter.id.eq(newsletterId))
                .fetchOne();
    }

    public Page<Newsletter> getScrapPaging(final Long memberId, final Pageable pageable) {
        List<Newsletter> content = getScrap(memberId, pageable);
        Long count = scrapCount(memberId);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Newsletter> getScrap(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterScraps, newsletterScrap)
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .where(newsletterScrap.member.id.eq(userId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    private Long scrapCount(final Long memberId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletter)
                .where(newsletterScrap.member.id.eq(memberId))
                .fetchOne();
    }
}
