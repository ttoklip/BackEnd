package com.api.ttoklip.domain.newsletter.post.repository;

import static com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment.newsletterComment;
import static com.api.ttoklip.domain.newsletter.post.domain.QNewsletter.newsletter;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterDefaultRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Newsletter> getHouseWork() {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .where(newsletter.category.eq(Category.HOUSEWORK))
                .limit(10)
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    public List<Newsletter> getRecipe() {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .where(newsletter.category.eq(Category.RECIPE))
                .limit(10)
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    public List<Newsletter> getSafeLiving() {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .where(newsletter.category.eq(Category.SAFE_LIVING))
                .limit(10)
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    public List<Newsletter> getWelfarePolicy() {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .where(newsletter.category.eq(Category.WELFARE_POLICY))
                .limit(10)
                .orderBy(newsletter.id.desc())
                .fetch();
    }
}
