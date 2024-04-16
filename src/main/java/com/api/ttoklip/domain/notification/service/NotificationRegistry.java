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
@Transactional(readOnly = true)
public class NotificationRegistry {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void register(final NotiCategory notiCategory, final Member member) {
        Notification notification = Notification.of(member, notiCategory.getTitle(), notiCategory.getText(), notiCategory);
        notificationRepository.save(notification);
    }

    public NotiCategory determineNotiCategory(final String className, final String methodName) {
        // 클래스 이름과 메서드 이름을 검사하여 알림 종류를 판단

        // ToDo Notification Entity 저장 로직
        // ToDo 알림 종류 판단 로직
        return NotiCategory.HONEY_TIP_COMMENT;
    }
}
