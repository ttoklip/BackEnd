package com.domain.notification.application;

import com.common.NotiCategory;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.cart.domain.Cart;
import com.domain.comment.domain.Comment;
import com.domain.community.domain.Community;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.member.domain.Member;
import com.domain.notification.domain.Notification;
import com.domain.notification.domain.NotificationRepository;
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
    public void register(final NotiCategory notiCategory, final Member member, final Long targetClassId,
                         final boolean status) {
        String title = notiCategory.getTitle();
        String text = notiCategory.getText();

        Object target = notiCategory.getTarget();
        String targetClassType = getTargetType(target);

        Notification notification = Notification.of(member, title, text, notiCategory, targetClassType, targetClassId,
                status);
        notificationRepository.save(notification);
    }

    private String getTargetType(final Object target) {
        if (!(target instanceof String targetString)) {
            throw new ApiException(ErrorType._BAD_CATEGORY_NOTIFICATION_TYPE);
        }

        if (HoneyTip.class.getSimpleName().equals(targetString)) {
            return HoneyTip.class.getSimpleName();
        }

        if (Community.class.getSimpleName().equals(targetString)) {
            return Community.class.getSimpleName();
        }

        if (Cart.class.getSimpleName().equals(targetString)) {
            return Cart.class.getSimpleName();
        }

        if (Comment.class.getSimpleName().equals(targetString)) {
            return Comment.class.getSimpleName();
        }

        throw new ApiException(ErrorType._BAD_CATEGORY_NOTIFICATION_TYPE);
    }
}
