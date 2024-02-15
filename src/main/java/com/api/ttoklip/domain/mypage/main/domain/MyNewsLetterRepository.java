package com.api.ttoklip.domain.mypage.main.domain;

import com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.QNewsletter;
import com.api.ttoklip.domain.newsletter.scarp.entity.QNewsletterScrap;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyNewsLetterRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QNewsletter newsletter = QNewsletter.newsletter;
    private final QNewsletterComment newsletterComment = QNewsletterComment.newsletterComment;
    private final QNewsletterScrap newsletterScrap = QNewsletterScrap.newsletterScrap;
    public Page<Newsletter> getScrapContain(final Long userId, final Pageable pageable){
        List<Newsletter> content = getSearchScrapPageId(userId,pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }
    private List<Newsletter> getSearchScrapPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .select(newsletter)
                .from(newsletter)
                .innerJoin(newsletterScrap).on(newsletter.id.eq(newsletterScrap.newsletter.id))
                .where(newsletterScrap.member.id.eq(userId))
                .distinct()
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletter)
                .distinct()
                .fetchOne();
    }

}
