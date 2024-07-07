package com.api.ttoklip.domain.mypage.term.domain;

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
public class TermPaginRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QTerm term = QTerm.term;

    public Page<Term> getContain(final Pageable pageable) {
        List<Term> terms = getPageContent(pageable);
        Long count = countQuery();
        return new PageImpl<>(terms, pageable, count);
    }

    private List<Term> getPageContent(final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(term)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(term.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(term)
                .fetchOne();
    }
}
