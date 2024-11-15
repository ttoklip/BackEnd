package com.domain.honeytip.infrastructure;

import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.QHoneyTip;
import com.domain.honeytip.domain.QHoneyTipComment;
import com.domain.honeytip.domain.QHoneyTipScrap;
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
public class HoneyTipScrapQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QHoneyTipScrap honeyTipScrap = QHoneyTipScrap.honeyTipScrap;
    private final QHoneyTip honeyTip = QHoneyTip.honeyTip;
    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;
    private final QMember member = QMember.member;
    private final QProfile profile = QProfile.profile;

    public Long countHoneyTipScrapByHoneyTipId(final Long postId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTipScrap)
                .where(honeyTipScrap.honeyTip.id.eq(postId))
                .fetchOne();
    }

    public Page<HoneyTip> getScrapPaging(final Long memberId, final Pageable pageable) {
        List<HoneyTip> content = getSearchHoneyTips(memberId, pageable);
        Long count = getSearchCount(memberId);
        return new PageImpl<>(content, pageable, count);
    }

    private List<HoneyTip> getSearchHoneyTips(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipScraps, honeyTipScrap)
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .leftJoin(honeyTip.member, member).fetchJoin()
                .leftJoin(honeyTip.member.profile, profile).fetchJoin()
                .where(honeyTipScrap.member.id.eq(userId).and(honeyTip.deleted.eq(false)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    public Long getSearchCount(final Long memberId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTipScrap)
                .where(honeyTipScrap.member.id.eq(memberId).and(honeyTip.deleted.eq(false)))
                .fetchOne();
    }
}
