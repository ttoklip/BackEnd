package com.domain.community.infrastructure;


import com.domain.community.domain.Community;
import com.domain.community.domain.QCommunity;
import com.domain.community.domain.QCommunityComment;
import com.domain.community.domain.QCommunityScrap;
import com.domain.member.domain.QMember;
import com.domain.profile.domain.QProfile;
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
public class CommunityScrapQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QCommunity community = QCommunity.community;
    private final QCommunityScrap communityScrap = QCommunityScrap.communityScrap;
    private final QCommunityComment communityComment = QCommunityComment.communityComment;
    private final QProfile profile = QProfile.profile;
    private final QMember member = QMember.member;

    public Long countCommunityScrapsByCommunityId(final Long communityId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(communityScrap)
                .where(communityScrap.community.id.eq(communityId))
                .fetchOne();
    }

    public Page<Community> getScrapPaging(final Long memberId, final Pageable pageable) {
        List<Community> content = getScrap(memberId, pageable);
        Long count = matchScrapCount(memberId);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Community> getScrap(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .leftJoin(community.communityScraps, communityScrap)
                .leftJoin(community.communityComments, communityComment)
                .leftJoin(community.member, member).fetchJoin()
                .leftJoin(community.member.profile, profile).fetchJoin()
                .where(
                        communityScrap.member.id.eq(userId),
                        community.deleted.eq(false)
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }

    private Long matchScrapCount(final Long memberId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .where(
                        communityScrap.member.id.eq(memberId),
                        community.deleted.eq(false)
                )
                .fetchOne();
    }
}
