package com.api.ttoklip.domain.newsletter.post.repository;

import com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.QNewsletter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class NewsletterSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QNewsletter newsletter = QNewsletter.newsletter;
    private final QNewsletterComment newsletterComment = QNewsletterComment.newsletterComment;

    public Page<Newsletter> getContain(final String keyword, final Pageable pageable) {
        List<Newsletter> content = getSearchPageTitle(keyword, pageable);
        Long count = countQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Newsletter> getSearchPageTitle(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .select(newsletter)
                .from(newsletter)
                .distinct()
                .where(
                        containTitle(keyword)
                )
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    private BooleanExpression containTitle(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return newsletter.title.contains(keyword);
        }
        return null;
    }

    private Long countQuery(final String keyword) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletter)
                .distinct()
                .where(
                        containTitle(keyword)
                )
                .fetchOne();
    }
}
