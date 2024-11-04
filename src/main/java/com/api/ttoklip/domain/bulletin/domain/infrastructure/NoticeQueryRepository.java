package com.api.ttoklip.domain.bulletin.domain.infrastructure;


import com.api.ttoklip.domain.bulletin.domain.Notice;
import com.api.ttoklip.domain.bulletin.domain.QNotice;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QNotice notice = QNotice.notice;

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

    public Page<Notice> getContain(final Pageable pageable) {
        List<Notice> notices = getPageContent(pageable);
        Long count = countQuery();
        return new PageImpl<>(notices, pageable, count);
    }

    private List<Notice> getPageContent(final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(notice)
                .where(notice.deleted.eq(false))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(notice.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(notice)
                .fetchOne();
    }

}
