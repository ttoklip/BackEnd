package com.api.ttoklip.domain.honeytip.post.repository;


import static com.api.ttoklip.domain.honeytip.like.domain.QHoneyTipLike.honeyTipLike;
import static com.api.ttoklip.domain.honeytip.scrap.domain.QHoneyTipScrap.honeyTipScrap;

import com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip;
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
public class HoneyTipSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QHoneyTip honeyTip = QHoneyTip.honeyTip;
    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;

    public Page<HoneyTip> getContain(final String keyword, final Pageable pageable, final String sort) {
        List<HoneyTip> content = getSearchPageTitleOrContent(keyword, pageable, sort);
        Long count = countQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<HoneyTip> getSearchPageTitleOrContent(final String keyword, final Pageable pageable,
                                                       final String sort) {
        JPAQuery<HoneyTip> query = defaultQuery(keyword, pageable);

        if (sort.equals("popularity")) {
            return sortPopularity(query);
        }

        if (sort.equals("latest")) {
            return sortLatest(query);
        }

        throw new ApiException(ErrorType.INVALID_SORT_TYPE);
    }

    private JPAQuery<HoneyTip> defaultQuery(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .where(
                        containTitle(keyword).or(containContent(keyword)),
                        getHoneyTipActivate()
                )
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .leftJoin(honeyTip.honeyTipLikes, honeyTipLike)
                .leftJoin(honeyTip.honeyTipScraps, honeyTipScrap)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
    }

    private BooleanExpression containTitle(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return honeyTip.title.contains(keyword);
        }
        return null;
    }

    private BooleanExpression containContent(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return honeyTip.content.contains(keyword);
        }
        return null;
    }

    private BooleanExpression getHoneyTipActivate() {
        return honeyTip.deleted.isFalse();
    }

    private List<HoneyTip> sortPopularity(final JPAQuery<HoneyTip> query) {
        // 댓글, 좋아요, 스크랩 수에 따라 인기 점수 계산
        return query
                .groupBy(honeyTip.id)
                .orderBy(
                        getLikeSize().add(
                                getCommentSize()
                        ).add(
                                getScrapSize()
                        ).desc()
                ).fetch();
    }

    private NumberExpression<Integer> getLikeSize() {
        return honeyTip.honeyTipLikes.size();
    }

    private NumberExpression<Integer> getCommentSize() {
        return honeyTip.honeyTipComments.size();
    }

    private NumberExpression<Integer> getScrapSize() {
        return honeyTip.honeyTipScraps.size();
    }

    private List<HoneyTip> sortLatest(final JPAQuery<HoneyTip> query) {
        return query
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    private Long countQuery(final String keyword) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTip)
                .where(
                        containTitle(keyword).or(containContent(keyword))
                )
                .fetchOne();
    }
}
