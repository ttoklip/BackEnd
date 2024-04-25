package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.domain.notification.entity.Notification;
import com.api.ttoklip.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void register(final NotiCategory notiCategory, final Member member, final boolean status) {
        log.info("NotificationService.register");
        String title = notiCategory.getTitle();
        String text = notiCategory.getText();
        Notification notification = Notification.of(member, title, text, notiCategory, status);
        log.info("notification = " + notification);
        notificationRepository.save(notification);
        log.info("notification = " + notification);
        log.info("NotificationService.register -- end");
    }
}
