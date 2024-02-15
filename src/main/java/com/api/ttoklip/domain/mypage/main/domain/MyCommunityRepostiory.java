package com.api.ttoklip.domain.mypage.main.domain;

import com.api.ttoklip.domain.town.community.comment.QCommunityComment;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.entity.QCommunity;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyCommunityRepostiory {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCommunity community = QCommunity.community;
    private final QCommunityComment communityComment = QCommunityComment.communityComment;

    public Page<Community> getContain(final Long userId, final Pageable pageable) {
        List<Community> content = getSearchPageId(userId ,pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<Community> getSearchPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .select(community)
                .from(community)
                .where(community.member.id.eq(userId))
                .distinct()
                .leftJoin(community.communityComments, communityComment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(community.id.desc())
                .fetch();
    }


    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(community)
                .distinct()
                .fetchOne();
    }
}
