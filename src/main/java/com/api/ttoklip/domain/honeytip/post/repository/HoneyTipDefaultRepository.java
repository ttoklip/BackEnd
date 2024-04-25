package com.api.ttoklip.domain.honeytip.post.repository;


import static com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment.honeyTipComment;
import static com.api.ttoklip.domain.honeytip.like.domain.QHoneyTipLike.honeyTipLike;
import static com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip.honeyTip;
import static com.api.ttoklip.domain.honeytip.scrap.domain.QHoneyTipScrap.honeyTipScrap;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipDefaultRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<HoneyTip> getHouseWork() {
        JPAQuery<HoneyTip> query = defaultQuery();

        return query
                .where(
                        honeyTip.category.eq(Category.HOUSEWORK),
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


    private BooleanExpression getHoneyTipActivate() {
        return honeyTip.deleted.isFalse();
    }

    public List<HoneyTip> getRecipe() {
        JPAQuery<HoneyTip> query = defaultQuery();

        return query
                .where(
                        honeyTip.category.eq(Category.RECIPE),
                        getHoneyTipActivate()
                )
                .fetch();
    }

    public List<HoneyTip> getSafeLiving() {
        JPAQuery<HoneyTip> query = defaultQuery();

        return query
                .where(
                        honeyTip.category.eq(Category.SAFE_LIVING),
                        getHoneyTipActivate()
                )
                .fetch();
    }

    public List<HoneyTip> getWelfarePolicy() {
        JPAQuery<HoneyTip> query = defaultQuery();
        return query
                .where(
                        honeyTip.category.eq(Category.WELFARE_POLICY),
                        getHoneyTipActivate()
                )
                .fetch();
    }

    public List<HoneyTip> getTop5() {
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
