package com.domain.notification.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface NotificationRepository {
    void save(Notification notification);
    List<Notification> findRecentNotifications(Long currentMemberId, Pageable pageable);
    List<Notification> findTop5RecentNotifications(Long currentMemberId, Pageable pageable);
    
}
