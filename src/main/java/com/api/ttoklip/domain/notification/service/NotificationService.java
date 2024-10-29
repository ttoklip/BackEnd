package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.notification.dto.response.NotificationFrontResponse;
import com.api.ttoklip.domain.notification.dto.response.NotificationFrontResponses;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.domain.notification.entity.Notification;
import com.api.ttoklip.domain.notification.repository.NotificationRepository;
import com.api.ttoklip.domain.notification.repository.NotificationRepositoryImpl;
import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationRepositoryImpl notificationRepositoryImpl;
    private final MemberService memberService;

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

    public NotificationFrontResponses findNotification(final Long currentMemberId, final Pageable pageRequest) {
        Member currentMember = memberService.findById(currentMemberId);

        List<Notification> top5RecentNotifications = notificationRepositoryImpl.findTop5RecentNotifications(
                currentMember.getId(), pageRequest
        );

        List<NotificationFrontResponse> responses = top5RecentNotifications.stream()
                .map(noti -> NotificationFrontResponse.of(
                                noti.getId(), noti.getTargetIndex(), noti.getTargetType(),
                                noti.getTitle(), noti.getText(), noti.isStatus()
                        )
                ).toList();

        return NotificationFrontResponses.from(responses);
    }
}
