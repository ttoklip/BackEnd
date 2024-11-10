package com.domain.honeytip.infrastructure;

import com.domain.honeytip.domain.QHoneyTipScrap;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipScrapQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QHoneyTipScrap honeyTipScrap = QHoneyTipScrap.honeyTipScrap;

    public Long countHoneyTipScrapByHoneyTipId(final Long postId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTipScrap)
                .where(honeyTipScrap.honeyTip.id.eq(postId))
                .fetchOne();
    }
}
