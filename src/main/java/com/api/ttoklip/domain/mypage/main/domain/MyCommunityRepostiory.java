package com.api.ttoklip.domain.mypage.main.domain;

import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.api.ttoklip.domain.town.community.comment.QCommunityComment.communityComment;
import static com.api.ttoklip.domain.town.community.post.entity.QCommunity.*;
import static com.api.ttoklip.domain.town.community.scrap.entity.QCommunityScrap.communityScrap;

@Repository
@RequiredArgsConstructor
public class MyCommunityRepostiory {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Community> getContain(final Long userId, final Pageable pageable) {
        List<Community> content = getSearchPageId(userId ,pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<Community> getSearchPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .where(community.member.id.eq(userId).and(community.deleted.eq(false)))
                .leftJoin(community.communityComments, communityComment)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }


    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .fetchOne();
    }

    public Page<Community> getScrapContain(final Long userId, final Pageable pageable){
        List<Community> content = getSearchScrapPageId(userId,pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }
    private List<Community> getSearchScrapPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .leftJoin(community.communityScraps, communityScrap)
                .leftJoin(community.communityComments, communityComment)
                .where(communityScrap.member.id.eq(userId).and(community.deleted.eq(false)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }
}
