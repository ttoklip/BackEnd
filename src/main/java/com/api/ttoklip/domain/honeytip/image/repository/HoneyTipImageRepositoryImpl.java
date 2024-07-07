package com.api.ttoklip.domain.honeytip.image.repository;


import static com.api.ttoklip.domain.honeytip.image.domain.QHoneyTipImage.*;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipImageRepositoryImpl implements HoneyTipImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean doAllImageIdsExist(List<Long> imageIds) {
        Long count = queryFactory
                .select(Wildcard.count)
                .from(honeyTipImage)
                .where(honeyTipImage.id.in(imageIds))
                .fetchOne();

        return count != null && count == imageIds.size();
    }

    @Override
    public void deleteByImageIds(List<Long> imageIds) {
        queryFactory
                .delete(honeyTipImage)
                .where(honeyTipImage.id.in(imageIds))
                .execute();
    }
}
