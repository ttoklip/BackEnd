package com.api.ttoklip.domain.notification.repository;

import com.api.ttoklip.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
