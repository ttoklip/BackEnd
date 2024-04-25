package com.api.ttoklip.domain.notification.repository;

import static com.api.ttoklip.domain.member.domain.QMember.*;
import static com.api.ttoklip.domain.notification.entity.QNotification.notification;
import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.domain.notification.entity.Notification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    private List<Notification> findRecentNotifications(NotiCategory notiCategory, Pageable pageable) {
        return queryFactory
                .selectFrom(notification)
                .leftJoin(notification.member, member).fetchJoin()
                .where(
                        notification.notiCategory.eq(notiCategory),
                        notification.member.id.eq(getCurrentMember().getId())
                )
                .orderBy(notification.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Notification> findTop5RecentNotifications(NotiCategory notiCategory) {
        return findRecentNotifications(notiCategory, PageRequest.of(0, 5));
    }
}
