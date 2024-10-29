package com.api.ttoklip.domain.town.community.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.privacy.domain.QProfile.profile;
import static com.api.ttoklip.domain.town.community.domain.QCommunityComment.communityComment;
import static com.api.ttoklip.domain.town.community.domain.QCommunityImage.communityImage;
import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.community.domain.CommunityComment;
import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.domain.town.community.domain.QCommunity;
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

    private static final String SPLIT_CRITERIA = " ";
    private final JPAQueryFactory jpaQueryFactory;
    private final QCommunity community = QCommunity.community;

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
    public List<Community> getRecent3(final TownCriteria townCriteria) {
        String writerStreet = getCurrentMember().getStreet();

        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .where(
                        getCommunityActivate(),
                        getLocationFilterByTownCriteria(townCriteria, writerStreet)
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

    public BooleanExpression getLocationFilterByTownCriteria(final TownCriteria townCriteria,
                                                             final String street) {
        String[] streetParts = splitStreet(street);  // 공통 메서드로 분리

        if (townCriteria.equals(TownCriteria.CITY)) {
            return filterByCity(streetParts);
        }

        if (townCriteria.equals(TownCriteria.DISTRICT)) {
            return filterByDistrict(streetParts);
        }

        if (townCriteria.equals(TownCriteria.TOWN)) {
            return filterByTown(streetParts);
        }

        throw new ApiException(ErrorType.INTERNAL_STREET_TYPE);
    }

    // 주소를 공백으로 분리하는 로직을 하나의 메서드로 추출
    private String[] splitStreet(final String street) {
        return street.split(SPLIT_CRITERIA);
    }

    // '시' 부분만 추출해서 필터링 (예: '서울특별시'로 시작하는 모든 주소)
    private BooleanExpression filterByCity(final String[] streetParts) {

        if (streetParts.length > 0) {
            String city = streetParts[0];
            return community.member.street.startsWith(city);
        }
        return null;
    }

    // '시'와 '구'가 모두 일치해야 함 (예: '서울특별시 서대문구')
    private BooleanExpression filterByDistrict(final String[] streetParts) {
        if (streetParts.length > 1) {
            String city = streetParts[0];
            String district = streetParts[1];
            return community.member.street.startsWith(city + SPLIT_CRITERIA + district);
        }
        return null;
    }

    // '시', '구', '동'이 모두 일치해야 함 (예: '서울특별시 서대문구 북가좌동')
    private BooleanExpression filterByTown(final String[] streetParts) {
        if (streetParts.length > 2) {
            String city = streetParts[0];
            String district = streetParts[1];
            String town = streetParts[2];
            return community.member.street.startsWith(city + SPLIT_CRITERIA + district + SPLIT_CRITERIA + town);
        }
        return null;
    }

}
