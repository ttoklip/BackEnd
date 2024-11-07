package com.api.ttoklip.domain.term.repository;

import com.api.ttoklip.domain.term.domain.QTerm;
import com.api.ttoklip.domain.term.domain.Term;
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
public class TermPagingRepository {
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
