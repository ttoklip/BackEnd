package com.api.ttoklip.domain.honeytip.image.repository;


import static com.api.ttoklip.domain.honeytip.image.domain.QHoneyTipImage.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipImageRepositoryImpl implements HoneyTipImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByHoneyTipId(final Long honeyTipId) {
        queryFactory
                .delete(honeyTipImage)
                .where(honeyTipImage.honeyTip.id.eq(honeyTipId))
                .execute();
    }
}
