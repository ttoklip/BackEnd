package com.api.ttoklip.domain.mypage.domain;


import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.domain.QHoneyTip;
import com.api.ttoklip.domain.honeytip.domain.QHoneyTipComment;
import com.api.ttoklip.domain.honeytip.domain.QHoneyTipScrap;
import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.domain.profile.domain.QProfile;
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
public class MyHoneyTipRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final QHoneyTip honeyTip = QHoneyTip.honeyTip;
    private final QHoneyTipScrap honeyTipScrap = QHoneyTipScrap.honeyTipScrap;
    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;
    private final QMember member = QMember.member;
    private final QProfile profile = QProfile.profile;

    public Page<HoneyTip> getContain(final Long userId, final Pageable pageable) {
        List<HoneyTip> content = getSearchPageId(userId, pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<HoneyTip> getSearchPageId(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .distinct()
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .leftJoin(honeyTip.member, member).fetchJoin()
                .leftJoin(honeyTip.member.profile, profile).fetchJoin()
                .where(honeyTip.member.id.eq(userId).and(honeyTip.deleted.eq(false)))
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

    public Page<HoneyTip> getScrapContain(final Long userId, final Pageable pageable) {
        List<HoneyTip> content = getSearchScrapPageId(userId, pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<HoneyTip> getSearchScrapPageId(final Long userId, final Pageable pageable) {
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
}
