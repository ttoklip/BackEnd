package com.api.ttoklip.domain.common.comment.repository;

import static com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment.honeyTipComment;
import static com.api.ttoklip.domain.member.domain.QMember.member;

import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoneyTipCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<HoneyTipComment> findCommentsByHoneyTipId(final Long honeyTipId) {
        return jpaQueryFactory
                .selectFrom(honeyTipComment)
                .distinct()
                .leftJoin(honeyTipComment.member, member).fetchJoin()
                .where(
                        matchHoneyTipId(honeyTipId)
                )
                .orderBy(
                        honeyTipComment.parent.id.asc().nullsFirst(),
                        honeyTipComment.createdDate.asc()
                )
                .fetch();
    }

    private BooleanExpression matchHoneyTipId(final Long honeyTipId) {
        return honeyTipComment.honeyTip.id.eq(honeyTipId);
    }
}
