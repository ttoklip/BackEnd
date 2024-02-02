package com.api.ttoklip.domain.honeytip.image.repository;

import static com.api.ttoklip.domain.honeytip.post.post.domain.QHoneytipImage.honeytipImage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipImageRepositoryImpl implements HoneyTipImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByHoneyTipId(final Long honeyTipId) {
        queryFactory
                .delete(honeytipImage)
                .where(honeytipImage.honeytip.id.eq(honeyTipId))
                .execute();
    }
}
