package com.domain.community.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.TownCriteria;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityComment;
import com.domain.community.domain.QCommunity;
import com.domain.community.domain.QCommunityComment;
import com.domain.community.domain.QCommunityImage;
import com.domain.community.domain.QCommunityLike;
import com.domain.community.domain.QCommunityScrap;
import com.domain.member.domain.QMember;
import com.domain.profile.domain.QProfile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class CommunityQueryRepository {

    private static final String SPLIT_CRITERIA = " ";
    private final JPAQueryFactory jpaQueryFactory;
    private final QCommunity community = QCommunity.community;
    private final QProfile profile = QProfile.profile;
    private final QMember member = QMember.member;
    private final QCommunityImage communityImage = QCommunityImage.communityImage;
    private final QCommunityComment communityComment = QCommunityComment.communityComment;
    private final QCommunityLike communityLike = QCommunityLike.communityLike;
    private final QCommunityScrap communityScrap = QCommunityScrap.communityScrap;

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

    public List<Community> getRecent3(final TownCriteria townCriteria, final String street) {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .where(
                        getCommunityActivate(),
                        getLocationFilterByTownCriteria(townCriteria, street)
                )
                .leftJoin(community.member, member).fetchJoin()
                .orderBy(community.id.desc())
                .limit(3)
                .fetch();
    }

    private BooleanExpression matchCommunityId(final Long communityId) {
        return communityComment.community.id.eq(communityId);
    }

    public Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable, final String street, final String sort) {
        List<Community> pageContent = getPageContent(townCriteria, pageable, street, sort);
        Long count = countQuery(townCriteria, street);
        return new PageImpl<>(pageContent, pageable, count);
    }

    private List<Community> getPageContent(final TownCriteria townCriteria, final Pageable pageable,
                                           final String street, final String sort) { // 수정된 부분
        JPAQuery<Community> query = jpaQueryFactory
                .selectFrom(community)
                .where(
                        getCommunityActivate(),
                        getLocationFilterByTownCriteria(townCriteria, street)
                )
                .leftJoin(community.member, member).fetchJoin()
                .leftJoin(community.member.profile, profile).fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        // 정렬 기준에 따라 orderBy 절 추가
        if ("popularity".equals(sort)) {
            return sortByPopularity(query);
        } else if ("comment".equals(sort)) {
            return sortByCommentCount(query);
        } else if ("scrap".equals(sort)) {
            return sortByScrapCount(query);
        } else {
            return sortByLatest(query);
        }
    }

    private List<Community> sortByPopularity(final JPAQuery<Community> query) {
        // 인기 점수 계산: 좋아요 수 + 댓글 수 + 스크랩 수
        return query
                .leftJoin(community.communityLikes, communityLike)
                .leftJoin(community.communityComments, communityComment)
                .leftJoin(community.communityScraps, communityScrap)
                .groupBy(community.id)
                .orderBy(
                        communityLike.count().add(
                                communityComment.count()
                        ).add(
                                communityScrap.count()
                        ).desc()
                )
                .fetch();
    }

    //Todo 좋아요, 스크랩 동률일때 최신순으로 정렬
    private List<Community> sortByCommentCount(final JPAQuery<Community> query) {
        return query
                .leftJoin(community.communityComments, communityComment)
                .groupBy(community.id)
                .orderBy(communityComment.count().desc())
                .fetch();
    }

    private List<Community> sortByScrapCount(final JPAQuery<Community> query) {
        return query
                .leftJoin(community.communityScraps, communityScrap)
                .groupBy(community.id)
                .orderBy(communityScrap.count().desc())
                .fetch();
    }

    private List<Community> sortByLatest(final JPAQuery<Community> query) {
        return query
                .orderBy(community.id.desc())
                .fetch();
    }

    private Long countQuery(final TownCriteria townCriteria, final String street) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .where(
                        getCommunityActivate(),
                        getLocationFilterByTownCriteria(townCriteria, street)
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

    public Page<Community> getContain(final String keyword, final Pageable pageable, final String sort) {
        List<Community> content = getSearchPageTitle(keyword, pageable, sort);
        Long count = countQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Community> getSearchPageTitle(final String keyword, final Pageable pageable, final String sort) {
        JPAQuery<Community> query = defaultQuery(keyword, pageable);

        if (sort.equals("popularity")) {
            return sortPopularity(query);
        }

        if (sort.equals("latest")) {
            return sortLatest(query);
        }

        throw new ApiException(ErrorType.INVALID_SORT_TYPE);
    }

    private JPAQuery<Community> defaultQuery(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .where(
                        containTitle(keyword).or(containContent(keyword)),
                        getCommunityActivate()
                )
                .leftJoin(community.communityComments, communityComment)
                .leftJoin(community.communityLikes, communityLike)
                .leftJoin(community.communityScraps, communityScrap)
                .leftJoin(community.member, member).fetchJoin()
                .leftJoin(community.member.profile, profile).fetchJoin()
                .groupBy(community.id)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
    }

    private BooleanExpression getCommunityActivate() {
        return community.deleted.isFalse();
    }

    private BooleanExpression containTitle(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return community.title.contains(keyword);
        }
        return Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression containContent(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return community.content.contains(keyword);
        }
        return Expressions.asBoolean(true).isTrue();
    }

    private List<Community> sortPopularity(final JPAQuery<Community> query) {
        // 댓글, 좋아요, 스크랩 수에 따라 인기 점수 계산
        return query
                .groupBy(community.id)
                .orderBy(
                        getLikeSize().add(
                                getCommentSize()
                        ).add(
                                getScrapSize()
                        ).desc()
                ).fetch();
    }

    private NumberExpression<Integer> getLikeSize() {
        return community.communityLikes.size();
    }

    private NumberExpression<Integer> getCommentSize() {
        return community.communityComments.size();
    }

    private NumberExpression<Integer> getScrapSize() {
        return community.communityScraps.size();
    }

    private List<Community> sortLatest(final JPAQuery<Community> query) {
        return query
                .orderBy(community.id.desc())
                .fetch();
    }

    private Long countQuery(final String keyword) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .where(
                        containTitle(keyword).or(containContent(keyword)),
                        getCommunityActivate()
                )
                .fetchOne();
    }

    public Page<Community> getMatchWriterPaging(final Long memberId, final Pageable pageable) {
        List<Community> content = matchWriter(memberId, pageable);
        Long count = matchWriterCount(memberId);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Community> matchWriter(final Long memberId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(community)
                .distinct()
                .where(
                        community.member.id.eq(memberId),
                        community.deleted.isFalse()
                )
                .leftJoin(community.communityComments, communityComment)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }


    private Long matchWriterCount(final Long currentMemberId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .where(
                        community.member.id.eq(currentMemberId),
                        community.deleted.isFalse()
                )
                .fetchOne();
    }
}