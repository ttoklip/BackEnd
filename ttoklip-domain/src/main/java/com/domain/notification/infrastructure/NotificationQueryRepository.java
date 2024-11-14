package com.domain.notification.infrastructure;

import com.domain.notification.domain.Notification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    private List<Notification> findRecentNotifications(final Long currentMemberId, final Pageable pageable) {
        return queryFactory
                .selectFrom(notification)
                .leftJoin(notification.member, member).fetchJoin()
                .where(
                        notification.member.id.eq(currentMemberId)
                )
                .orderBy(notification.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Notification> findTop5RecentNotifications(final Long currentMemberId, final Pageable pageable) {
        return findRecentNotifications(currentMemberId, pageable);
    }
}
