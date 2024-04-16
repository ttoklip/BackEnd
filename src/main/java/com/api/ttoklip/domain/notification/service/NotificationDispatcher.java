package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.common.comment.Comment;
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
public class NotificationDispatcher {

    private final FCMService fcmService;
    private final MemberService memberService;
    private final NotificationRegistry notificationRegistry;
    private final NotificationTargetFilter filter;

    public void dispatchNotification(final NotificationRequest request) {
        NotiCategory notiCategory = notificationRegistry.determineNotiCategory(request.className(),
                request.methodName());
        dispatch(request.targetMemberId(), notiCategory);
    }

    // 댓글 알림 타겟 결정 및 알림 전송
    public void dispatchCommentNotification(final Long targetMemberId, final Comment comment) {
        NotiCategory notiCategory = notificationRegistry.determineCommentNotiCategory(comment);
        if (notiCategory.equals(NotiCategory.BAD_TYPE_NOTIFICATION)) {
            log.info("[NOTICE BAD TYPE] 잘못된 로그가 반환되어 알림 AOP를 종료합니다.");
            return;
        }
        // 게시글 작성자에게 알릴 여부
        boolean notifyPostWriter = notiCategory.isNotifyPostWriter();
        // 댓글 작성자에게 알릴 여부
        boolean notifyCommentWriter = notiCategory.isNotifyCommentWriter();

        // 댓글이 생성됨을 게시글 작성자에게 알려야하는데 현재 사용자가 게시글 작성자가 맞다면
        if (notifyPostWriter) {
            if (filter.determinePostNoticeTarget(comment)) {
                dispatch(targetMemberId, notiCategory);
            }
        }

        // 답글이고 & 답글이 생성됨을 댓글 작성자에게 알려야하는데 현재 사용자가 댓글 작성자가 맞다면
        if (notifyCommentWriter && (comment.getParent() != null)) {
            if (filter.determineCommentNoticeTarget(comment)) {
                dispatch(targetMemberId, notiCategory);
            }
        }
    }

    private void dispatch(final Long targetMemberId, final NotiCategory notiCategory) {
        Member findMember = memberService.findById(targetMemberId);
        String fcmToken = findMember.getFcmToken();
        validFCMToken(fcmToken);

        notificationRegistry.register(notiCategory, findMember);
        fcmService.sendNotification(notiCategory, fcmToken);
        log.info("[Send Notification Success]" + Message.sendAlarmSuccess().getMessage());
    }

    private void validFCMToken(final String fcmToken) {
        if (fcmToken == null || fcmToken.isBlank()) {
            log.error("[Send Notification ERROR] --- ERROR !!");
            throw new ApiException(ErrorType._USER_FCM_TOKEN_NOT_FOUND);
        }
    }
}
