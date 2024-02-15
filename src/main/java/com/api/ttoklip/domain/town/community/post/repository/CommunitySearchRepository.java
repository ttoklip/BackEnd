package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.comment.QCommunityComment;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.entity.QCommunity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class CommunitySearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCommunity community = QCommunity.community;

    private final QCommunityComment communityComment = QCommunityComment.communityComment;

    public Page<Community> getContain(final String keyword, final Pageable pageable) {
        List<Community> content = getSearchPageTitle(keyword, pageable);
        Long count = countQuery(keyword);

        return new PageImpl<>(content, pageable, count);
    }

    private List<Community> getSearchPageTitle(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .select(community)
                .from(community)
                .distinct()
                .where(
                        containTitle(keyword)
                )
                .leftJoin(community.communityComments, communityComment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }

    private BooleanExpression containTitle(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return community.title.contains(keyword);
        }
        return null;
    }

    private Long countQuery(final String keyword) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .distinct()
                .where(
                        containTitle(keyword)
                )
                .fetchOne();
    }
}
