package com.api.ttoklip.domain.mypage.main.domain;

import com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip;

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
public class MyHoneyTipRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QHoneyTip honeyTip = QHoneyTip.honeyTip;
    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;

    public Page<HoneyTip> getContain(final Long userId, final Pageable pageable){
        List<HoneyTip> content = getSearchPageId(userId,pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }
    private List<HoneyTip> getSearchPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .select(honeyTip)
                .from(honeyTip)
                .where(honeyTip.member.id.eq(userId))
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(honeyTip.id.desc())
                .fetch();
    }
    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTip)
                .distinct()
                .fetchOne();
    }
}
