package com.api.ttoklip.domain.newsletter.post.domain.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.QNewsletter;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class NewsletterQueryDslRepositoryImpl implements NewsletterQueryDslRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Newsletter> findLatestNewslettersByCategory(Category category, int limit) {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        return queryFactory.selectFrom(qNewsletter)
                .where(qNewsletter.category.eq(category))
                .orderBy(qNewsletter.createdDate.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Newsletter> findRandomNewslettersByCategory(Category category, int limit) {
        QNewsletter qNewsletter = QNewsletter.newsletter;
        return queryFactory.selectFrom(qNewsletter)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(limit)
                .fetch();
    }
}
