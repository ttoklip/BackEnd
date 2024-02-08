package com.api.ttoklip.domain.mypage.noti.post.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticePagingRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QNotice notice=QNotice.notice;

    private List<Notice> getPageContent(final Pageable pageable){
        return jpaQueryFactory
                .select(notice)
                .from(notice)
                .distinct()
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(notice.id.desc())
                .fetch();
    }
}
