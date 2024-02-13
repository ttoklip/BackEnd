package com.api.ttoklip.domain.manager.inquiry.domain;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.api.ttoklip.domain.manager.inquiry.domain.QFaq.faq;

@Repository
@RequiredArgsConstructor
public class FaqPagingRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QFaq faq = QFaq.faq;

    public Page<Faq> getContain(final Pageable pageable) {
        List<Faq> faqs = getPageContent(pageable);
        Long count = countQuery();
        return new PageImpl<>(faqs, pageable, count);
    }

    private List<Faq> getPageContent(final Pageable pageable) {
        return jpaQueryFactory
                .select(faq)
                .from(faq)
                .distinct()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(faq.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(faq)
                .distinct()
                .fetchOne();
    }

}
