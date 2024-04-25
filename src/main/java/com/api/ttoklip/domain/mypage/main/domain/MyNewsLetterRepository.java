package com.api.ttoklip.domain.mypage.main.domain;


import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment.newsletterComment;
import static com.api.ttoklip.domain.newsletter.post.domain.QNewsletter.newsletter;
import static com.api.ttoklip.domain.newsletter.scarp.entity.QNewsletterScrap.newsletterScrap;

@Repository
@RequiredArgsConstructor
public class MyNewsLetterRepository {
    private final JPAQueryFactory jpaQueryFactory;
    public Page<Newsletter> getScrapContain(final Long userId, final Pageable pageable){
        List<Newsletter> content = getSearchScrapPageId(userId,pageable);
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
