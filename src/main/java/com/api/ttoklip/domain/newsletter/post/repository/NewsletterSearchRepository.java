package com.api.ttoklip.domain.newsletter.post.repository;

import static com.api.ttoklip.domain.newsletter.like.entity.QNewsletterLike.newsletterLike;
import static com.api.ttoklip.domain.newsletter.scarp.entity.QNewsletterScrap.newsletterScrap;

import com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.QNewsletter;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
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

    public Page<Newsletter> getContain(final String keyword, final Pageable pageable, final String sort) {
        List<Newsletter> content = getSearchPageTitle(keyword, pageable, sort);
        Long count = countQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Newsletter> getSearchPageTitle(final String keyword, final Pageable pageable, final String sort) {
        JPAQuery<Newsletter> query = defaultQuery(keyword, pageable);

        if (sort.equals("popularity")) {
            return sortPopularity(query);
        }

        if (sort.equals("latest")) {
            return sortLatest(query);
        }

        throw new ApiException(ErrorType.INVALID_SORT_TYPE);
    }

    private List<Newsletter> sortPopularity(final JPAQuery<Newsletter> query) {
        return query
                .groupBy(newsletter.id)
                .orderBy(
                        getLikeSize().add(
                                getCommentSize()
                        ).add(
                                getScrapSize()
                        ).desc()
                ).fetch();
    }

    private NumberExpression<Integer> getLikeSize() {
        return newsletter.newsletterLikes.size();
    }

    private NumberExpression<Integer> getCommentSize() {
        return newsletter.newsletterComments.size();
    }

    private NumberExpression<Integer> getScrapSize() {
        return newsletter.newsletterScraps.size();
    }

    private JPAQuery<Newsletter> defaultQuery(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .select(newsletter)
                .from(newsletter)
                .distinct()
                .where(
                        containTitle(keyword)
                )
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .leftJoin(newsletter.newsletterLikes, newsletterLike)
                .leftJoin(newsletter.newsletterScraps, newsletterScrap)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
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

    private List<Newsletter> sortLatest(final JPAQuery<Newsletter> query) {
        return query
                .orderBy(newsletter.id.desc())
                .fetch();
    }
}
