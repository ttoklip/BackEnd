package com.api.ttoklip.domain.stranger.repository;

import static com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment.honeyTipComment;
import static com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip.honeyTip;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
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
public class StrangerHoneyTipRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<HoneyTip> getContain(final Long targetId, final Pageable pageable) {
        List<HoneyTip> content = getSearchPageId(targetId, pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<HoneyTip> getSearchPageId(final Long targetId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .where(honeyTip.member.id.eq(targetId).and(honeyTip.deleted.eq(false)))
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTip)
                .fetchOne();
    }
}
