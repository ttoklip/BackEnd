package com.api.notification.application;

import com.api.global.support.response.Message;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.notification.domain.Notification;
import com.domain.notification.domain.NotificationRepository;
import com.api.notification.presentation.UpdateFCMTokenRequest;
import com.domain.notification.dto.response.NotificationFrontResponse;
import com.domain.notification.dto.response.NotificationFrontResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationRepository notificationRepository;
    private final MemberService memberService;


    public NotificationFrontResponses findNotification(final Long currentMemberId, final Pageable pageable) {
        Member currentMember = memberService.getById(currentMemberId);

        List<Notification> top5RecentNotifications = notificationRepository.findTop5RecentNotifications(
                currentMember.getId(), pageable
        );

        List<NotificationFrontResponse> responses = top5RecentNotifications.stream()
                .map(noti -> NotificationFrontResponse.of(
                                noti.getId(), noti.getTargetIndex(), noti.getTargetType(),
                                noti.getTitle(), noti.getText(), noti.isStatus()
                        )
                ).toList();

        return NotificationFrontResponses.from(responses);
    }

    public Message updateMemberFCMToken(final Long memberId, final UpdateFCMTokenRequest request) {
        memberService.updateMemberFCMToken(memberId, request.fcmToken());
        return Message.updateFCM();
    }
}
