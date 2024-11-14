package com.domain.notification.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.cart.domain.Cart;
import com.domain.comment.domain.Comment;
import com.domain.community.domain.Community;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.member.domain.Member;
import com.domain.notification.domain.Notification;
import com.domain.notification.domain.NotificationRepository;
import com.domain.notification.domain.vo.NotiCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private String getTargetType(Object target) {
        if (target instanceof Class<?> clazz) {

            if (clazz.equals(HoneyTip.class)) {
                return HoneyTip.class.getSimpleName();
            }

            if (clazz.equals(Community.class)) {
                return Community.class.getSimpleName();
            }

            if (clazz.equals(Cart.class)) {
                return Cart.class.getSimpleName();
            }

            if (clazz.equals(Comment.class)) {
                return Comment.class.getSimpleName();
            }
        }

        throw new ApiException(ErrorType._BAD_CATEGORY_NOTIFICATION_TYPE);
    }
}
