//package com.domain.honeytip.infrastructure;
//
//import com.domain.honeytip.domain.QHoneyTipLike;
//import com.querydsl.core.types.dsl.Wildcard;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class HoneyTipLikeQueryRepository {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    private final QHoneyTipLike honeyTipLike = QHoneyTipLike.honeyTipLike;
//
//    public Long countHoneyTipLikesByHoneyTipId(final Long postId) {
//        return jpaQueryFactory
//                .select(Wildcard.count)
//                .from(honeyTipLike)
//                .where(honeyTipLike.honeyTip.id.eq(postId))
//                .fetchOne();
//    }
//
//}
