package com.api.ttoklip.domain.bulletin.domain;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticePagingRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QNotice notice = QNotice.notice;

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
