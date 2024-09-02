package com.api.ttoklip.domain.newsletter.post.repository;

import static com.api.ttoklip.domain.newsletter.comment.domain.QNewsletterComment.newsletterComment;
import static com.api.ttoklip.domain.newsletter.post.domain.QNewsletter.newsletter;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterDefaultRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Newsletter> getHouseWork() {
        return getNewsletter10Desc(Category.HOUSEWORK);
    }

    public List<Newsletter> getRecipe() {
        return getNewsletter10Desc(Category.RECIPE);
    }

    public List<Newsletter> getSafeLiving() {
        return getNewsletter10Desc(Category.SAFE_LIVING);
    }

    public List<Newsletter> getWelfarePolicy() {
        return getNewsletter10Desc(Category.HOUSEWORK);
    }

    private List<Newsletter> getNewsletter10Desc(final Category category) {
        return jpaQueryFactory
                .selectFrom(newsletter)
                .distinct()
                .leftJoin(newsletter.newsletterComments, newsletterComment)
                .where(
                        getMatchCateGory(category)
                        ,
                        getActivatedNewsletter()

                )
                .limit(10)
                .orderBy(newsletter.id.desc())
                .fetch();
    }

    private static BooleanExpression getMatchCateGory(final Category housework) {
        return newsletter.category.eq(housework);
    }

    private BooleanExpression getActivatedNewsletter() {
        return newsletter.deleted.isFalse();
    }
}
