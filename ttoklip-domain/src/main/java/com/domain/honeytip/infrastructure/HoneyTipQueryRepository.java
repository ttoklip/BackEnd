package com.domain.honeytip.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.Category;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.QHoneyTip;
import com.domain.honeytip.domain.QHoneyTipComment;
import com.domain.honeytip.domain.QHoneyTipImage;
import com.domain.honeytip.domain.QHoneyTipLike;
import com.domain.honeytip.domain.QHoneyTipScrap;
import com.domain.honeytip.domain.QHoneyTipUrl;
import com.domain.member.domain.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class HoneyTipQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;

    private final QHoneyTip honeyTip = QHoneyTip.honeyTip;
    private final QHoneyTipLike honeyTipLike = QHoneyTipLike.honeyTipLike;
    private final QHoneyTipScrap honeyTipScrap = QHoneyTipScrap.honeyTipScrap;

    private final QHoneyTipImage honeyTipImage = QHoneyTipImage.honeyTipImage;
    private final QHoneyTipUrl honeyTipUrl = QHoneyTipUrl.honeyTipUrl;

    private final QMember member = QMember.member;

    public HoneyTip findByIdActivated(final Long honeyTipId) {
        HoneyTip findHoneyTip = jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.member, member).fetchJoin()
                .where(
                        getHoneyTipActivate(),
                        matchId(honeyTipId)
                )
                .fetchOne();
        return Optional.ofNullable(findHoneyTip)
                .orElseThrow(() -> new ApiException(ErrorType.HONEY_TIP_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long honeyTipId) {
        return honeyTip.id.eq(honeyTipId);
    }

    public HoneyTip findHoneyTipWithDetails(final Long honeyTipId) {
        HoneyTip findHoneyTip = jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipImages, honeyTipImage)
                .leftJoin(honeyTip.honeyTipUrls, honeyTipUrl)
                .leftJoin(honeyTip.member, member).fetchJoin()
                .where(
                        getHoneyTipActivate(),
                        matchId(honeyTipId)
                )
                .fetchOne();

        return Optional.ofNullable(findHoneyTip)
                .orElseThrow(() -> new ApiException(ErrorType.HONEY_TIP_NOT_FOUND));
    }

    public Page<HoneyTip> matchCategoryPaging(final Category category, final Pageable pageable) {
        List<HoneyTip> pageContent = getPageContent(category, pageable);
        Long count = pagingCountQuery(category);
        return new PageImpl<>(pageContent, pageable, count);
    }

    private List<HoneyTip> getPageContent(final Category category, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .where(
                        getHoneyTipActivate(),
                        matchCategory(category)
                )
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .leftJoin(honeyTip.honeyTipLikes, honeyTipLike)
                .leftJoin(honeyTip.honeyTipScraps, honeyTipScrap)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    private Long pagingCountQuery(final Category category) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTip)
                .where(
                        getHoneyTipActivate(),
                        matchCategory(category)
                )
                .fetchOne();
    }

    private BooleanExpression matchCategory(final Category category) {
        return honeyTip.category.eq(category);
    }

    public List<HoneyTip> findRecent3() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .where(
                        getHoneyTipActivate()
                )
                .orderBy(honeyTip.id.desc())
                .limit(3)
                .fetch();
    }

    public List<HoneyTip> findHouseworkTips() {
        JPAQuery<HoneyTip> query = defaultQuery();

        return query
                .where(
                        matchCategory(Category.HOUSEWORK),
                        getHoneyTipActivate()
                )
                .fetch();
    }

    private JPAQuery<HoneyTip> defaultQuery() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .leftJoin(honeyTip.honeyTipLikes, honeyTipLike)
                .leftJoin(honeyTip.honeyTipScraps, honeyTipScrap)
                .limit(10)
                .orderBy(honeyTip.id.desc());
    }

    public List<HoneyTip> findRecipeTips() {
        JPAQuery<HoneyTip> query = defaultQuery();

        return query
                .where(
                        getHoneyTipActivate(),
                        matchCategory(Category.RECIPE)
                )
                .fetch();
    }

    public List<HoneyTip> findSafeLivingTips() {
        JPAQuery<HoneyTip> query = defaultQuery();

        return query
                .where(
                        getHoneyTipActivate(),
                        matchCategory(Category.SAFE_LIVING)
                )
                .fetch();
    }

    public List<HoneyTip> findWelfarePolicyTips() {
        JPAQuery<HoneyTip> query = defaultQuery();
        return query
                .where(
                        getHoneyTipActivate(),
                        matchCategory(Category.WELFARE_POLICY)
                )
                .fetch();
    }

    public List<HoneyTip> getPopularityTop5() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .leftJoin(honeyTip.honeyTipLikes, honeyTipLike)
                .leftJoin(honeyTip.honeyTipScraps, honeyTipScrap)
                .where(
                        getHoneyTipActivate()
                )
                .groupBy(honeyTip.id)
                .orderBy(
                        getLikeSize()
                                .add(getCommentSize())
                                .add(getScrapSize())
                                .desc(),
                        honeyTip.createdDate.desc()
                )
                .limit(5)
                .fetch();
    }

    public Page<HoneyTip> getContain(final String keyword, final Pageable pageable, final String sort) {
        List<HoneyTip> content = getSearchPageTitleOrContent(keyword, pageable, sort);
        Long count = containCountQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<HoneyTip> getSearchPageTitleOrContent(final String keyword, final Pageable pageable,
                                                       final String sort) {
        JPAQuery<HoneyTip> query = defaultQuery(keyword, pageable);

        if (sort.equals("popularity")) {
            return sortPopularity(query);
        }

        if (sort.equals("latest")) {
            return sortLatest(query);
        }

        throw new ApiException(ErrorType.INVALID_SORT_TYPE);
    }

    private JPAQuery<HoneyTip> defaultQuery(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .where(
                        containTitle(keyword).or(containContent(keyword)),
                        getHoneyTipActivate()
                )
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .leftJoin(honeyTip.honeyTipLikes, honeyTipLike)
                .leftJoin(honeyTip.honeyTipScraps, honeyTipScrap)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
    }

    private BooleanExpression containTitle(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return honeyTip.title.contains(keyword);
        }
        return null;
    }

    private BooleanExpression containContent(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return honeyTip.content.contains(keyword);
        }
        return null;
    }

    private BooleanExpression getHoneyTipActivate() {
        return honeyTip.deleted.isFalse();
    }

    private List<HoneyTip> sortPopularity(final JPAQuery<HoneyTip> query) {
        // 댓글, 좋아요, 스크랩 수에 따라 인기 점수 계산
        return query
                .groupBy(honeyTip.id)
                .orderBy(
                        getLikeSize().add(
                                getCommentSize()
                        ).add(
                                getScrapSize()
                        ).desc()
                ).fetch();
    }

    private NumberExpression<Integer> getLikeSize() {
        return honeyTip.honeyTipLikes.size();
    }

    private NumberExpression<Integer> getCommentSize() {
        return honeyTip.honeyTipComments.size();
    }

    private NumberExpression<Integer> getScrapSize() {
        return honeyTip.honeyTipScraps.size();
    }

    private List<HoneyTip> sortLatest(final JPAQuery<HoneyTip> query) {
        return query
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    private Long containCountQuery(final String keyword) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTip)
                .where(
                        containTitle(keyword).or(containContent(keyword))
                )
                .fetchOne();
    }

    public Page<HoneyTip> matchWriterPaging(final Long targetId, final Pageable pageable) {
        List<HoneyTip> content = getUserWrite(targetId, pageable);
        Long count = writerCountQuery(targetId);
        return new PageImpl<>(content, pageable, count);
    }

    private List<HoneyTip> getUserWrite(final Long targetId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .where(honeyTip.member.id.eq(targetId).and(honeyTip.deleted.eq(false)))
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    private Long writerCountQuery(final Long targetId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTip)
                .where(honeyTip.member.id.eq(targetId).and(honeyTip.deleted.eq(false)))
                .fetchOne();
    }
}
