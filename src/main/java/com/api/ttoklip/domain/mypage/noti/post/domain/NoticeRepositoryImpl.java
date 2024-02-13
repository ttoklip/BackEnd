package com.api.ttoklip.domain.mypage.noti.post.domain;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.api.ttoklip.domain.mypage.noti.post.domain.QNotice.notice;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Notice findByIdActivated(final Long noticeId) {
        Notice findNotice = jpaQueryFactory
                .selectFrom(notice)
                .distinct()
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
