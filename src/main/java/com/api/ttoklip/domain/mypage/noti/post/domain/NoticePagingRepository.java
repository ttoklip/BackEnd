package com.api.ttoklip.domain.mypage.noti.post.domain;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;

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
                .select(notice)
                .from(notice)
                .distinct()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(notice.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(notice)
                .distinct()
                .fetchOne();
    }

}
