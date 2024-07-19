package com.api.ttoklip.domain.bulletin.domain;


import static com.api.ttoklip.domain.bulletin.domain.QNotice.notice;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Notice findByIdActivated(final Long noticeId) {
        Notice findNotice = jpaQueryFactory
                .selectFrom(notice)
                .where(
                        matchId(noticeId), getNoticeActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findNotice)
                .orElseThrow(() -> new ApiException(ErrorType.NOTICE_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long noticeId) {
        return notice.id.eq(noticeId);
    }

    private BooleanExpression getNoticeActivate() {
        return notice.deleted.isFalse();
    }

}
