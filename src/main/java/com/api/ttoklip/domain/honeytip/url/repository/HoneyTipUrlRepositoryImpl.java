package com.api.ttoklip.domain.honeytip.url.repository;


import static com.api.ttoklip.domain.honeytip.url.domain.QHoneyTipUrl.*;

import com.api.ttoklip.domain.honeytip.url.domain.QHoneyTipUrl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipUrlRepositoryImpl implements HoneyTipUrlRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByIds(final List<Long> ids) {
        queryFactory
                .delete(honeyTipUrl)
                .where(honeyTipUrl.id.in(ids))
                .execute();
    }
}
