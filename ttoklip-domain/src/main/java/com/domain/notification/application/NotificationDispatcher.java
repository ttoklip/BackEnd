package com.domain.notification.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.comment.domain.Comment;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.common.NotiCategory;
import com.domain.notification.dto.request.NotificationRequest;
import com.domain.notification.dto.response.NotificationInternalResponse;
import com.infrastructure.notification.service.FCMService;
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
    private final NotificationCommentTargetFinder commentTargetFinder;
    private final NotificationPostTargetFinder postTargetFinder;
    private final NotificationService notificationService;

    public void dispatchNotification(final NotificationRequest request) {

        NotiCategory notiCategory = request.notiCategory();
        Long targetIndex = request.targetIndex();

        // 게시글 작성자에게 알릴 여부
        boolean notifyPostWriter = notiCategory.isNotifyPostWriter();
        // 댓글 작성자에게 알릴 여부
        boolean notifyCommentWriter = notiCategory.isNotifyCommentWriter();

        if (notifyPostWriter) {
            Optional<NotificationInternalResponse> optionalPostWriterId = postTargetFinder.getPostWriterId(notiCategory,
                    targetIndex);

            if (optionalPostWriterId.isPresent()) {
                NotificationInternalResponse res = optionalPostWriterId.get();
                dispatch(res.targetMemberId(), res.targetClassId(), notiCategory, request.fromMemberId());
            }
        }

        if (notifyCommentWriter) {
            Optional<NotificationInternalResponse> optionalCommentWriterId = postTargetFinder.getCommentWriterId(
                    notiCategory,
                    targetIndex);

            if (optionalCommentWriterId.isPresent()) {
                NotificationInternalResponse res = optionalCommentWriterId.get();
                dispatch(res.targetMemberId(), res.targetClassId(), notiCategory, request.fromMemberId());
            }
        }

    }

    // 댓글 알림 타겟 결정 및 알림 전송
    public void dispatchCommentNotification(final Comment comment, final NotiCategory notiCategory, final Long fromMemberId) {

        // 게시글 작성자에게 알릴 여부
        boolean notifyPostWriter = notiCategory.isNotifyPostWriter();
        // 댓글 작성자에게 알릴 여부
        boolean notifyCommentWriter = notiCategory.isNotifyCommentWriter();

        // (댓글과 답글 모두의 경우) 댓글이 생성됨을 게시글 작성자에게 알려야하므로 게시글 작성자의 ID로 전송
        if (notifyPostWriter) {
            Optional<NotificationInternalResponse> optionalResponse = commentTargetFinder.getPostWriterId(comment);
            if (optionalResponse.isPresent()) {
                NotificationInternalResponse res = optionalResponse.get();
                dispatch(res.targetMemberId(), res.targetClassId(), notiCategory, fromMemberId);
            }
        }

        // 답글이고 & 답글이 생성됨을 댓글 작작성자에게 알려야하므로 댓글(부모 댓글) 작성자의 ID로 전송
        if (notifyCommentWriter && (comment.getParent() != null)) {
            NotificationInternalResponse res = commentTargetFinder.getParentCommentWriterId(comment);
            dispatch(res.targetMemberId(), res.targetClassId(), notiCategory, fromMemberId);
        }
    }

    private void dispatch(final Long targetMemberId, final Long targetClassId, final NotiCategory notiCategory,
                          final Long fromMemberId) {
        // 알림 받을 대상이 본인인 경우 전송 x
        if (isSelfNotification(targetMemberId, fromMemberId)) {
            return;
        }

        Member findMember = memberService.getById(targetMemberId);
        String fcmToken = findMember.getFcmToken();

        try {
            validFCMToken(fcmToken);    // 롤백시 comment 또한 롤백, notofication entity가 생성되지 않음
        } catch (ApiException e) {
            log.info("error code = {}", e.getErrorType().getStatus());
            log.error(e.getMessage());
        }

        sendFcmNotification(targetClassId, notiCategory, findMember);

        log.info("[Send Notification Success]");
    }

    private boolean isSelfNotification(final Long targetMemberId, final Long fromMemberId) {
        return targetMemberId.equals(fromMemberId);
    }

    private void validFCMToken(final String fcmToken) {
        if (fcmToken == null || fcmToken.isBlank()) {
            log.error("[Send Notification ERROR] --- ERROR !!");
            throw new ApiException(ErrorType._USER_FCM_TOKEN_NOT_FOUND);
        }
    }

    // ToDo 추후에 kafka로 변경
    private void sendFcmNotification(final Long targetClassId, final NotiCategory notiCategory, final Member findMember) {
        try {
            fcmService.sendNotification(notiCategory, findMember.getFcmToken());
            notificationService.register(notiCategory, findMember, targetClassId, true);
        } catch (IllegalArgumentException e) {
            log.info("FCMService.sendNotification + IllegalArgumentException");
            e.printStackTrace();
            notificationService.register(notiCategory, findMember, targetClassId, false);
        } catch (Exception e) {
            e.printStackTrace();
            notificationService.register(notiCategory, findMember, targetClassId, false);
        }
    }
}
