package com.api.ttoklip.domain.mypage.domain;

import static com.api.ttoklip.domain.member.domain.QMember.*;
import static com.api.ttoklip.domain.privacy.domain.QProfile.*;
import static com.api.ttoklip.domain.town.community.comment.QCommunityComment.communityComment;
import static com.api.ttoklip.domain.town.community.post.entity.QCommunity.community;
import static com.api.ttoklip.domain.town.community.scrap.entity.QCommunityScrap.communityScrap;

import com.api.ttoklip.domain.town.community.post.entity.Community;
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
public class MyCommunityPagingRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Community> getContain(final Long currentMemberId, final Pageable pageable) {
        List<Community> content = getSearchPageId(currentMemberId, pageable);
        Long count = countQuery(currentMemberId);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Community> getSearchPageId(final Long currentMemberId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .where(
                        community.member.id.eq(currentMemberId),
                        community.deleted.isFalse()
                )
                .leftJoin(community.communityComments, communityComment)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }


    private Long countQuery(final Long currentMemberId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .where(
                        community.member.id.eq(currentMemberId),
                        community.deleted.isFalse()
                )
                .fetchOne();
    }

    public Page<Community> getScrapContain(final Long currentMemberId, final Pageable pageable) {
        List<Community> content = getSearchScrapPageId(currentMemberId, pageable);
        Long count = countQuery(currentMemberId);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Community> getSearchScrapPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .leftJoin(community.communityScraps, communityScrap)
                .leftJoin(community.communityComments, communityComment)
                .leftJoin(community.member, member).fetchJoin()
                .leftJoin(community.member.profile, profile).fetchJoin()
                .where(communityScrap.member.id.eq(userId).and(community.deleted.eq(false)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }
}
