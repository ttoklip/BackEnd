package com.domain.notification.infrastructure;

import com.domain.member.domain.QMember;
import com.domain.notification.domain.Notification;
import com.domain.notification.domain.QNotification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QMember member = QMember.member;
    private final QNotification notification = QNotification.notification;

    // ToDo 여기 로직 이상함 추후에 쿼리 수정
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
