package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.notification.dto.request.NotificationRequest;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMNotificationService {

    private final MemberService memberService;
    private final FCMManager fcmManager;

    public void sendNotification(final NotificationRequest request) {
        Member member = memberService.findById(request.targetMemberId());
        String fcmToken = member.getFcmToken();
        validFCMToken(fcmToken);

        NotiCategory notiCategory = judgeNotiCategory(request.className(), request.methodName());
        fcmManager.send(notiCategory, fcmToken);
        log.info("[Send Notification Success]" + Message.sendAlarmSuccess().getMessage());
    }

    private NotiCategory judgeNotiCategory(final String className, final String methodName) {
        // ToDo Notification Entity 저장 로직

        // ToDo 알림 종류 판단 로직
        return NotiCategory.HONEY_TIP_SCRAP;
    }


    private void validFCMToken(final String fcmToken) {
        if (fcmToken == null || fcmToken.isBlank()) {
            log.error("[Send Notification ERROR] --- ERROR !!");
            throw new ApiException(ErrorType._USER_FCM_TOKEN_NOT_FOUND);
        }
    }

}
