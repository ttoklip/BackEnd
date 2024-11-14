//package com.domain.honeytip.infrastructure;
//
//import com.domain.honeytip.domain.HoneyTip;
//import com.domain.honeytip.domain.QHoneyTipScrap;
//import com.querydsl.core.types.dsl.Wildcard;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class HoneyTipScrapQueryRepository {
//
//    private final JPAQueryFactory jpaQueryFactory;
//    private final QHoneyTipScrap honeyTipScrap = QHoneyTipScrap.honeyTipScrap;
//
//    public Long countHoneyTipScrapByHoneyTipId(final Long postId) {
//        return jpaQueryFactory
//                .select(Wildcard.count)
//                .from(honeyTipScrap)
//                .where(honeyTipScrap.honeyTip.id.eq(postId))
//                .fetchOne();
//    }
//
//    public Page<HoneyTip> getScrapPaging(final Long userId, final Pageable pageable) {
//        List<HoneyTip> content = getSearchHoneyTips(userId, pageable);
//        Long count = getSearchCount();
//        return new PageImpl<>(content, pageable, count);
//    }
//
//    private List<HoneyTip> getSearchHoneyTips(final Long userId, final Pageable pageable) {
//        return jpaQueryFactory
//                .selectFrom(honeyTip)
//                .distinct()
//                .leftJoin(honeyTip.honeyTipScraps, honeyTipScrap)
//                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
//                .leftJoin(honeyTip.member, member).fetchJoin()
//                .leftJoin(honeyTip.member.profile, profile).fetchJoin()
//                .where(honeyTipScrap.member.id.eq(userId).and(honeyTip.deleted.eq(false)))
//                .limit(pageable.getPageSize())
//                .offset(pageable.getOffset())
//                .orderBy(honeyTip.id.desc())
//                .fetch();
//    }
//
//    public Long getSearchCount(final Long postId) {
//        return jpaQueryFactory
//                .select(Wildcard.count)
//                .from(honeyTipScrap)
//                .where(honeyTipScrap.member.id.eq(userId).and(honeyTip.deleted.eq(false)))
//                .fetchOne();
//    }
//}
