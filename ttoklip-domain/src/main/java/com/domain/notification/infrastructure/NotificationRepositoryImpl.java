package com.domain.notification.infrastructure;

import com.domain.notification.domain.Notification;
import com.domain.notification.domain.NotificationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository jpaRepository;
//    private final NotificationQueryRepository queryDSLRepository;


    @Override
    public void save(final Notification notification) {
        jpaRepository.save(notification);
    }

    @Override
    public List<Notification> findRecentNotifications(final Long currentMemberId, final Pageable pageable) {
//        return queryDSLRepository.findTop5RecentNotifications(currentMemberId, pageable);
        return null;
    }

    @Override
    public List<Notification> findTop5RecentNotifications(final Long currentMemberId, final Pageable pageable) {
//        return queryDSLRepository.findTop5RecentNotifications(currentMemberId, pageable);
        return null;
    }
}
