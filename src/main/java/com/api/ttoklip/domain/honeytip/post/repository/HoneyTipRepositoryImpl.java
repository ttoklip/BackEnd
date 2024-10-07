package com.api.ttoklip.domain.honeytip.post.repository;


import static com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment.honeyTipComment;
import static com.api.ttoklip.domain.honeytip.image.domain.QHoneyTipImage.honeyTipImage;
import static com.api.ttoklip.domain.honeytip.like.domain.QHoneyTipLike.honeyTipLike;
import static com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip.honeyTip;
import static com.api.ttoklip.domain.honeytip.scrap.domain.QHoneyTipScrap.honeyTipScrap;
import static com.api.ttoklip.domain.honeytip.url.domain.QHoneyTipUrl.honeyTipUrl;
import static com.api.ttoklip.domain.member.domain.QMember.member;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
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

@RequiredArgsConstructor
public class HoneyTipRepositoryImpl implements HoneyTipRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
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

    private BooleanExpression getHoneyTipActivate() {
        return honeyTip.deleted.isFalse();
    }

    @Override
    public HoneyTip findHoneyTipWithDetails(final Long honeyTipId) {
        HoneyTip findHoneyTip = jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipImageList, honeyTipImage)
                .leftJoin(honeyTip.honeyTipUrlList, honeyTipUrl)
                .leftJoin(honeyTip.member, member).fetchJoin()
                .where(
                        getHoneyTipActivate(),
                        matchId(honeyTipId)
                )
                .fetchOne();

        return Optional.ofNullable(findHoneyTip)
                .orElseThrow(() -> new ApiException(ErrorType.HONEY_TIP_NOT_FOUND));
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<HoneyTip> findRecipeTips() {
        JPAQuery<HoneyTip> query = defaultQuery();

        return query
                .where(
                        getHoneyTipActivate(),
                        matchCategory(Category.RECIPE)
                )
                .fetch();
    }

    @Override
    public List<HoneyTip> findSafeLivingTips() {
        JPAQuery<HoneyTip> query = defaultQuery();

        return query
                .where(
                        getHoneyTipActivate(),
                        matchCategory(Category.SAFE_LIVING)
                )
                .fetch();
    }

    @Override
    public List<HoneyTip> findWelfarePolicyTips() {
        JPAQuery<HoneyTip> query = defaultQuery();
        return query
                .where(
                        getHoneyTipActivate(),
                        matchCategory(Category.WELFARE_POLICY)
                )
                .fetch();
    }

    @Override
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
                                .desc()
                )
                .limit(5)
                .fetch();
    }

    private NumberExpression<Integer> getScrapSize() {
        return honeyTip.honeyTipScraps.size();
    }

    private NumberExpression<Integer> getCommentSize() {
        return honeyTip.honeyTipComments.size();
    }

    private NumberExpression<Integer> getLikeSize() {
        return honeyTip.honeyTipLikes.size();
    }

}
