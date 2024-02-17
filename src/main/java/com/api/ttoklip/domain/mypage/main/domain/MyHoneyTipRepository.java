package com.api.ttoklip.domain.mypage.main.domain;


import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment.honeyTipComment;
import static com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip.*;
import static com.api.ttoklip.domain.honeytip.scrap.domain.QHoneyTipScrap.honeyTipScrap;


@Repository
@RequiredArgsConstructor
public class MyHoneyTipRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<HoneyTip> getContain(final Long userId, final Pageable pageable){
        List<HoneyTip> content = getSearchPageId(userId,pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }
    private List<HoneyTip> getSearchPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .select(honeyTip)
                .from(honeyTip)
                .where(honeyTip.member.id.eq(userId).and(honeyTip.deleted.eq(false)))
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(honeyTip.id.desc())
                .fetch();
    }
    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTip)
                .distinct()
                .fetchOne();
    }

    public Page<HoneyTip> getScrapContain(final Long userId, final Pageable pageable){
        List<HoneyTip> content = getSearchScrapPageId(userId,pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }
    private List<HoneyTip> getSearchScrapPageId(final Long userId, final Pageable pageable) {
         return jpaQueryFactory
                .selectFrom(honeyTip)
                .leftJoin(honeyTip.honeyTipScraps, honeyTipScrap)
////                .leftJoin(honeyTip.honeyTipScraps, QHoneyTipScrap.honeyTipScrap)
                .where(honeyTipScrap.member.id.eq(userId).and(honeyTip.deleted.eq(false)))
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(honeyTip.id.desc())
                .fetch();

    }
}
