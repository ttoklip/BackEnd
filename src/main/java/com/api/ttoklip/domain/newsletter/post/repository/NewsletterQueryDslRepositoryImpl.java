package com.api.ttoklip.domain.newsletter.post.repository;

import static com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment.newsletterComment;
import static com.api.ttoklip.domain.town.community.scrap.entity.QCommunityScrap.communityScrap;

import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.image.domain.QNewsletterImage;
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
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NewsletterQueryDslRepositoryImpl implements NewsletterQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final NewsletterRepository newsletterRepository;

    @Override
    public Newsletter findByIdFetchJoin(Long postId) {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        QNewsletterImage newsletterImage = QNewsletterImage.newsletterImage;

        Newsletter findNewsletter = jpaQueryFactory
                .selectFrom(qNewsletter)
                .distinct()
                .leftJoin(qNewsletter.newsletterImageList, newsletterImage)
                .fetchJoin()
                .where(qNewsletter.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(findNewsletter)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    @Override
    public List<NewsletterComment> findActiveCommentsByNewsletterId(Long postId) {
        return jpaQueryFactory
                .selectFrom(newsletterComment)
                .distinct()
                .where(
                        matchNewsletterId(postId)
                )
                .orderBy(
                        newsletterComment.parent.id.asc().nullsFirst(),
                        newsletterComment.createdDate.asc()
                )
                .fetch();
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

    private BooleanExpression matchNewsletterId(final Long newsletterId) {
        return newsletterComment.newsletter.id.eq(newsletterId);
    }

    private BooleanExpression getCommentActivate() {
        return newsletterComment.deleted.isFalse();
    }

    @Override
    public Newsletter findByIdActivated(Long id) {
        return newsletterRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    @Override
    public Long countNewsletterScrapsByNewsletterId(final Long communityId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(communityScrap)
                .where(communityScrap.community.id.eq(communityId))
                .fetchOne();
    }


}
