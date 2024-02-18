package com.api.ttoklip.domain.newsletter.post.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment.newsletterComment;
import static com.api.ttoklip.domain.newsletter.image.domain.QNewsletterImage.newsletterImage;
import static com.api.ttoklip.domain.newsletter.post.domain.QNewsletter.newsletter;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.QNewsletter;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class NewsletterQueryDslRepositoryImpl implements NewsletterQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Newsletter findByIdActivated(final Long newsletterId) {
        Newsletter findNewsletter = jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.member, member)
//                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .fetchJoin()
                .where(
                        matchId(newsletterId), getNewsletterActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findNewsletter)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long newsletterId) {
        return newsletter.id.eq(newsletterId);
    }

    private BooleanExpression getNewsletterActivate() {
        return newsletter.deleted.isFalse();
    }

    @Override
    public Newsletter findByIdFetchJoin(Long newsletterPostId) {
        Newsletter findNewsletter = jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterImages, newsletterImage)
                .leftJoin(newsletter.member, member)
                .fetchJoin()
                .where(
                        getNewsletterActivate(),
                        newsletter.id.eq(newsletterPostId)
                )
                .fetchOne();

        return Optional.ofNullable(findNewsletter)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    @Override
    public List<NewsletterComment> findActiveCommentsByNewsletterId(Long newsletterId) {
        return jpaQueryFactory
                .selectFrom(newsletterComment)
                .distinct()
                .where(
                        matchNewsletterId(newsletterId)
                )
                .leftJoin(newsletter.member, member)
                .orderBy(
                        newsletterComment.parent.id.asc().nullsFirst(),
                        newsletterComment.createdDate.asc()
                )
                .fetch();
    }

    private BooleanExpression matchNewsletterId(final Long newsletterId) {
        return newsletterComment.newsletter.id.eq(newsletterId);
    }

    @Override
    public Long findNewsletterCount() {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(qNewsletter)
                .distinct()
                .fetchOne();
    }

    @Override
    public Page<Newsletter> getPaging(final Category category, final Pageable pageable) {
        List<Newsletter> pageContent = getPageContent(category, pageable);
        Long count = countQuery(category);
        return new PageImpl<>(pageContent, pageable, count);
    }

    private List<Newsletter> getPageContent(final Category category, final Pageable pageable) {
        return jpaQueryFactory
                .select(newsletter)
                .from(newsletter)
                .distinct()
                .where(
                        matchCategory(category),
                        getNewsletterActivate()
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    private BooleanExpression matchCategory(final Category category) {
        return newsletter.category.eq(category);
    }

    private Long countQuery(final Category category) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletter)
                .distinct()
                .where(
                        matchCategory(category),
                        getNewsletterActivate()
                )
                .fetchOne();
    }

}
