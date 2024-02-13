package com.api.ttoklip.domain.manager.inquiry.domain;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static com.api.ttoklip.domain.manager.inquiry.domain.QInquiry.inquiry;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryPagingRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QInquiry inquiry=QInquiry.inquiry;

    public Page<Inquiry> getContain(final Pageable pageable) {
        List<Inquiry> notices = getPageContent(pageable);
        Long count = countQuery();
        return new PageImpl<>(notices, pageable, count);
    }

    private List<Inquiry> getPageContent(final Pageable pageable) {
        return jpaQueryFactory
                .select(inquiry)
                .from(inquiry)
                .distinct()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(inquiry.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(inquiry)
                .distinct()
                .fetchOne();
    }


}
