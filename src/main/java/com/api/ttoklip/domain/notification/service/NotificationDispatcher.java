package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.notification.dto.request.NotificationRequest;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import java.util.Optional;
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
    private final NotificationTargetFinder finder;

    public void dispatchNotification(final NotificationRequest request) {
        NotiCategory notiCategory = notificationRegistry.determineNotiCategory(request.className(),
                request.methodName());
//         dispatch(, notiCategory);
    }

    // 댓글 알림 타겟 결정 및 알림 전송
    public void dispatchCommentNotification(final Comment comment) {
        // 어떤 게시글의 알림인지 판단
        NotiCategory notiCategory = notificationRegistry.determineCommentNotiCategory(comment);
        if (notiCategory.equals(NotiCategory.BAD_TYPE_NOTIFICATION)) {
            log.info("[NOTICE BAD TYPE] 잘못된 로그가 반환되어 알림 AOP를 종료합니다.");
            return;
        }
        // 게시글 작성자에게 알릴 여부
        boolean notifyPostWriter = notiCategory.isNotifyPostWriter();
        // 댓글 작성자에게 알릴 여부
        boolean notifyCommentWriter = notiCategory.isNotifyCommentWriter();

        // (댓글과 답글 모두의 경우) 댓글이 생성됨을 게시글 작성자에게 알려야하므로 게시글 작성자의 ID로 전송
        if (notifyPostWriter) {
            Optional<Long> optionalWriterId = finder.getPostWriterId(comment);
            if (optionalWriterId.isPresent()) {
                Long writerId = optionalWriterId.get();
                dispatch(writerId, notiCategory);
            }
        }

        // 답글이고 & 답글이 생성됨을 댓글 작작성자에게 알려야하므로 댓글(부모 댓글) 작성자의 ID로 전송
        if (notifyCommentWriter && (comment.getParent() != null)) {
            Long parentCommentWriterId = finder.getParentCommentWriterId(comment);
            dispatch(parentCommentWriterId, notiCategory);
        }
    }

    private void dispatch(final Long targetMemberId, final NotiCategory notiCategory) {
        log.info("========== NotificationDispatcher.dispatch");
        Member findMember = memberService.findById(targetMemberId);
        String fcmToken = findMember.getFcmToken();

        try {
            validFCMToken(fcmToken);    // 롤백시 comment 또한 롤백, notofication entity가 생성되지 않음
        } catch (ApiException e) {
            log.info("error code = {}", e.getErrorType().getStatus());
            log.error(e.getMessage());
        }
        fcmService.sendNotification(notiCategory, findMember);

         log.info("[Send Notification Success]" + Message.sendAlarmSuccess(findMember.getEmail()).getMessage());
    }

    private void validFCMToken(final String fcmToken) {
        if (fcmToken == null || fcmToken.isBlank()) {
            log.error("[Send Notification ERROR] --- ERROR !!");
            throw new ApiException(ErrorType._USER_FCM_TOKEN_NOT_FOUND);
        }
    }
}
