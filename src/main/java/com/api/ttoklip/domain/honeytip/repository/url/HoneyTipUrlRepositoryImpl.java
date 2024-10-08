package com.api.ttoklip.domain.honeytip.repository.url;

import com.api.ttoklip.domain.honeytip.domain.QHoneyTipUrl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipUrlRepositoryImpl implements HoneyTipUrlRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QHoneyTipUrl honeyTipUrl = QHoneyTipUrl.honeyTipUrl;

    @Override
    public void deleteAllByIds(final List<Long> ids) {
        queryFactory
                .delete(honeyTipUrl)
                .where(honeyTipUrl.id.in(ids))
                .execute();
    }
}
