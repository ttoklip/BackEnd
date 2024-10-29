package com.api.ttoklip.domain.town.community.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.privacy.domain.QProfile.profile;
import static com.api.ttoklip.domain.town.community.like.entity.QCommunityLike.communityLike;
import static com.api.ttoklip.domain.town.community.scrap.entity.QCommunityScrap.communityScrap;

import com.api.ttoklip.domain.town.community.comment.QCommunityComment;
import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.domain.town.community.post.entity.QCommunity;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
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
                        containTitle(keyword),
                        getCommunityActivate()
                )
                .leftJoin(community.communityComments, communityComment)
                .leftJoin(community.communityLikes, communityLike)
                .leftJoin(community.communityScraps, communityScrap)
                .leftJoin(community.member, member).fetchJoin()
                .leftJoin(community.member.profile, profile).fetchJoin()
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
        return null;
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
                        containTitle(keyword),
                        getCommunityActivate()
                )
                .fetchOne();
    }
}
