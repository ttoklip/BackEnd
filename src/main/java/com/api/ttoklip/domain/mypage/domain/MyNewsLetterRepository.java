package com.api.ttoklip.domain.mypage.domain;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.QNewsletter;
import com.api.ttoklip.domain.newsletter.domain.QNewsletterComment;
import com.api.ttoklip.domain.newsletter.domain.QNewsletterScrap;
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
public class MyNewsLetterRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final QNewsletter newsletter = QNewsletter.newsletter;
    private final QNewsletterComment newsletterComment = QNewsletterComment.newsletterComment;
    private final QNewsletterScrap newsletterScrap = QNewsletterScrap.newsletterScrap;

    public Page<Newsletter> getScrapContain(final Long userId, final Pageable pageable) {
        List<Newsletter> content = getSearchScrapPageId(userId, pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<Newsletter> getSearchScrapPageId(final Long userId, final Pageable pageable) {
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

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletter)
                .fetchOne();
    }

}
