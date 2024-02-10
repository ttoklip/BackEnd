package com.api.ttoklip.domain.honeytip.post.repository;


import static com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment.honeyTipComment;
import static com.api.ttoklip.domain.honeytip.like.domain.QHoneyTipLike.honeyTipLike;
import static com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip.honeyTip;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipDefaultRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<HoneyTip> getHouseWork() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .where(honeyTip.category.eq(Category.HOUSEWORK))
                .limit(10)
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    public List<HoneyTip> getRecipe() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .where(honeyTip.category.eq(Category.RECIPE))
                .limit(10)
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    public List<HoneyTip> getSafeLiving() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .where(honeyTip.category.eq(Category.SAFE_LIVING))
                .limit(10)
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    public List<HoneyTip> getWelfarePolicy() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .where(honeyTip.category.eq(Category.WELFARE_POLICY))
                .limit(10)
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    public List<HoneyTip> getTop5() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .leftJoin(honeyTip.honeyTipLikes, honeyTipLike)
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .groupBy(honeyTip.id)
                .orderBy(   // ToDo 스크랩 수 계산
                        getLikeSize().add(getCommentSize())
                                .desc()
                )
                .limit(5)
                .fetch();
    }

    private NumberExpression<Integer> getCommentSize() {
        return honeyTip.honeyTipComments.size();
    }

    private NumberExpression<Integer> getLikeSize() {
        return honeyTip.honeyTipLikes.size();
    }
}
