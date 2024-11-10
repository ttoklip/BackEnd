package com.domain.honeytip.infrastructure;

import com.domain.honeytip.domain.QHoneyTipUrl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipUrlQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QHoneyTipUrl honeyTipUrl = QHoneyTipUrl.honeyTipUrl;

    public void deleteAllByIds(final List<Long> ids) {
        queryFactory
                .delete(honeyTipUrl)
                .where(honeyTipUrl.id.in(ids))
                .execute();
    }
}
