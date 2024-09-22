package com.api.ttoklip.domain.town.community.post.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.privacy.domain.QProfile.profile;
import static com.api.ttoklip.domain.town.LocationCriteriaFilter.getLocationFilterByTownCriteria;
import static com.api.ttoklip.domain.town.community.comment.QCommunityComment.communityComment;
import static com.api.ttoklip.domain.town.community.image.entity.QCommunityImage.communityImage;
import static com.api.ttoklip.domain.town.community.post.entity.QCommunity.community;
import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Community findByIdActivated(final Long communityId) {
        Community findCommunity = jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .leftJoin(community.member, member).fetchJoin()
                .where(
                        matchId(communityId), getCommunityActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findCommunity)
                .orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long communityId) {
        return community.id.eq(communityId);
    }

    private BooleanExpression getCommunityActivate() {
        return community.deleted.isFalse();
    }

    @Override
    public Community findByIdFetchJoin(final Long communityPostId) {
        Community findCommunity = jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .leftJoin(community.communityImages, communityImage)
                .leftJoin(community.member, member)
                .fetchJoin()
                .where(
                        getCommunityActivate(),
                        community.id.eq(communityPostId)
                )
                .fetchOne();

        return Optional.ofNullable(findCommunity)
                .orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUND));
    }

    @Override
    public List<CommunityComment> findActiveCommentsByCommunityId(final Long communityId) {
        return jpaQueryFactory
                .selectFrom(communityComment)
                .distinct()
                .where(
                        matchCommunityId(communityId)
                )
                .leftJoin(communityComment.member, member)
                .orderBy(
                        communityComment.parent.id.asc().nullsFirst(),
                        communityComment.createdDate.asc()
                )
                .fetch();
    }

    @Override
    public List<Community> getRecent3() {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .where(
                        getCommunityActivate()
                )
                .leftJoin(community.member, member).fetchJoin()
                .orderBy(community.id.desc())
                .limit(3)
                .fetch();
    }

    private BooleanExpression matchCommunityId(final Long communityId) {
        return communityComment.community.id.eq(communityId);
    }

    @Override
    public Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable) {
        List<Community> pageContent = getPageContent(townCriteria, pageable);
        Long count = countQuery(townCriteria);
        return new PageImpl<>(pageContent, pageable, count);
    }

    private List<Community> getPageContent(final TownCriteria townCriteria, final Pageable pageable) {
        String writerStreet = getCurrentMember().getStreet();

        return jpaQueryFactory
                .selectFrom(community)
                .where(
                        getCommunityActivate(),
                        getLocationFilterByTownCriteria(townCriteria, writerStreet)
                )
                .leftJoin(community.member, member).fetchJoin()
                .leftJoin(community.member.profile, profile).fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }

    private Long countQuery(final TownCriteria townCriteria) {
        String writerStreet = getCurrentMember().getStreet();
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .where(
                        getCommunityActivate(),
                        getLocationFilterByTownCriteria(townCriteria, writerStreet)
                )
                .fetchOne();
    }

}
