package com.api.ttoklip.domain.newsletter.repository.post;

import static com.api.ttoklip.domain.member.domain.QMember.member;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.QNewsletter;
import com.api.ttoklip.domain.newsletter.domain.QNewsletterComment;
import com.api.ttoklip.domain.newsletter.domain.QNewsletterImage;
import com.api.ttoklip.domain.newsletter.domain.QNewsletterLike;
import com.api.ttoklip.domain.newsletter.domain.QNewsletterScrap;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class NewsletterQueryRepository {

    private static final String POPULARITY = "popularity";
    private static final String LATEST = "latest";
    private final JPAQueryFactory jpaQueryFactory;
    private final QNewsletter newsletter = QNewsletter.newsletter;
    private final QNewsletterImage newsletterImage = QNewsletterImage.newsletterImage;
    private final QNewsletterLike newsletterLike = QNewsletterLike.newsletterLike;
    private final QNewsletterScrap newsletterScrap = QNewsletterScrap.newsletterScrap;

    private final QNewsletterComment newsletterComment = QNewsletterComment.newsletterComment;


    public Newsletter findByIdActivated(final Long newsletterId) {
        Newsletter findNewsletter = jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.member, member).fetchJoin()
                .where(
                        getActivatedNewsletter(),
                        matchId(newsletterId)
                )
                .fetchOne();
        return Optional.ofNullable(findNewsletter)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long newsletterId) {
        return newsletter.id.eq(newsletterId);
    }

    private BooleanExpression getActivatedNewsletter() {
        return newsletter.deleted.isFalse();
    }


    public Newsletter findByIdFetchJoin(final Long newsletterPostId) {
        Newsletter findNewsletter = jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterImages, newsletterImage)
                .leftJoin(newsletter.member, member).fetchJoin()
                .where(
                        getActivatedNewsletter(),
                        matchId(newsletterPostId)
                )
                .fetchOne();

        return Optional.ofNullable(findNewsletter)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }


    public Long findNewsletterCount() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletter)
                .fetchOne();
    }


    public Page<Newsletter> getPaging(final Category category, final Pageable pageable) {
        List<Newsletter> pageContent = getPageContent(category, pageable);
        Long count = countQuery(category);
        return new PageImpl<>(pageContent, pageable, count);
    }

    private List<Newsletter> getPageContent(final Category category, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .where(
                        matchCategory(category),
                        getActivatedNewsletter()
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    private BooleanExpression matchCategory(final Category category) {
        return newsletter.category.eq(category);
    }

    private Long countQuery(final Category category) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletter)
                .where(
                        getActivatedNewsletter(),
                        matchCategory(category)
                )
                .fetchOne();
    }


    public List<Newsletter> getRecent3() {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .where(
                        getActivatedNewsletter()
                )
                .orderBy(newsletter.id.desc())
                .limit(3)
                .fetch();
    }


    public List<Newsletter> findRandom4ActiveNewsletters() {
        long count = getNewsletterCount();

        // 전체 개수 내에서 랜덤한 인덱스 생성
        // 랜덤 인덱스의 최대값을 조정하여 초과하지 않도록 설정
        // 최소값을 0으로 설정하여 인덱스가 음수가 되지 않도록 함
        int maxIndex = (int) Math.max(0, count - 4);
        int randomIndex = new Random().nextInt(maxIndex + 1);

        // 랜덤한 인덱스에서 시작하여 4개의 결과 가져오기
        return jpaQueryFactory.selectFrom(newsletter)
                .where(
                        getActivatedNewsletter()
                )
                .offset(randomIndex)
                .limit(4)
                .fetch();
    }

    private long getNewsletterCount() {
        Long count = jpaQueryFactory.select(Wildcard.count)
                .from(newsletter)
                .where(
                        getActivatedNewsletter()
                )
                .fetchOne();

        if (count == null) {
            return 0;
        }

        return count;
    }

    public List<Newsletter> getHouseWorkNewsletter10Desc() {
        return getNewsletter10Desc(Category.HOUSEWORK);
    }

    public List<Newsletter> getRecipeNewsletter10Desc() {
        return getNewsletter10Desc(Category.RECIPE);
    }

    public List<Newsletter> getSafeLivingNewsletter10Desc() {
        return getNewsletter10Desc(Category.SAFE_LIVING);
    }

    public List<Newsletter> getWelfarePolicyNewsletter10Desc() {
        return getNewsletter10Desc(Category.WELFARE_POLICY);
    }

    private List<Newsletter> getNewsletter10Desc(final Category category) {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .where(
                        getActivatedNewsletter(),
                        matchCategory(category)
                )
                .limit(10)
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    public Page<Newsletter> getContain(final String keyword, final Pageable pageable, final String sort) {
        List<Newsletter> content = getSearchPageTitleOrContent(keyword, pageable, sort);
        Long count = countQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Newsletter> getSearchPageTitleOrContent(final String keyword, final Pageable pageable,
                                                         final String sort) {
        JPAQuery<Newsletter> query = defaultQuery(keyword, pageable);

        if (sort.equals(POPULARITY)) {
            return sortPopularity(query);
        }

        if (sort.equals(LATEST)) {
            return sortLatest(query);
        }

        throw new ApiException(ErrorType.INVALID_SORT_TYPE);
    }

    private JPAQuery<Newsletter> defaultQuery(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .where(
                        containTitle(keyword).or(containContent(keyword))
                )
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .leftJoin(newsletter.newsletterLikes, newsletterLike)
                .leftJoin(newsletter.newsletterScraps, newsletterScrap)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
    }

    private BooleanExpression containTitle(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return newsletter.title.contains(keyword);
        }
        return null;
    }

    private BooleanExpression containContent(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return newsletter.content.contains(keyword);
        }
        return null;
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

    private Long countQuery(final String keyword) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(newsletter)
                .where(
                        containTitle(keyword).or(containContent(keyword))
                )
                .fetchOne();
    }

    private List<Newsletter> sortLatest(final JPAQuery<Newsletter> query) {
        return query
                .orderBy(newsletter.id.desc())
                .fetch();
    }
}
