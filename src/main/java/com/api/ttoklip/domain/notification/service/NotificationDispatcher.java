package com.api.ttoklip.domain.notification.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.notification.dto.request.NotificationRequest;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
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

    public void dispatchNotification(final NotificationRequest request) {
        NotiCategory notiCategory = notificationRegistry.determineNotiCategory(request.className(), request.methodName());
        dispatch(request.targetMemberId(), notiCategory);
    }

    // 댓글 알림 타겟 결정 및 알림 전송
    public void dispatchCommentNotification(final Long targetMemberId, final Comment comment) {
        NotiCategory notiCategory = notificationRegistry.determineCommentNotiCategory(comment);

        // 게시글 작성자에게 알릴 여부
        boolean notifyPostWriter = notiCategory.isNotifyPostWriter();
        // 댓글 작성자에게 알릴 여부
        boolean notifyCommentWriter = notiCategory.isNotifyCommentWriter();

        // 댓글이 생성됨을 게시글 작성자에게 알려야하는데 현재 사용자가 게시글 작성자가 아니라면
        if (notifyPostWriter) {
            if (!determinePostNoticeTarget(comment)) {
                return;
            }
        }

        // 답글이 생성됨을 댓글 작성자에게 알려야하는데 현재 사용자가 댓글 작성자가 아니라면
        if (notifyCommentWriter) {
            if (!determineCommentNoticeTarget(comment)) {
                return;
            }
        }
            
        if (notiCategory.equals(NotiCategory.BAD_TYPE_NOTIFICATION)) {
            log.info("[NOTICE BAD TYPE] 잘못된 로그가 반환되어 알림 AOP를 종료합니다.");
            return;
        }


        dispatch(targetMemberId, notiCategory);
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

    // ToDo 댓글작성자에게 알림 보낼지 여부
    private boolean determineCommentNoticeTarget(final Comment comment) {

        return false;
    }

    // ToDo FetchJoin
    private boolean determinePostNoticeTarget(final Comment comment) {
        // 꿀팁공유해요 작성자가 맞는지 필터링
        if (comment instanceof HoneyTipComment honeyTipComment) {
            Long honeyTipWriterId = honeyTipComment.getHoneyTip().getMember().getId();
            if (getCurrentMember().getId().equals(honeyTipWriterId)) {
                return true;
            }
        }

        // 질문해요 작성자가 맞는지 필터링
        if (comment instanceof QuestionComment questionComment) {
            Long questionWriterId = questionComment.getQuestion().getMember().getId();
            if (getCurrentMember().getId().equals(questionWriterId)) {
                return true;
            }
        }

        // 함께해요 작성자가 맞는지 필터링
        if (comment instanceof CartComment cartComment) {
            Long cartWriterId = cartComment.getCart().getMember().getId();
            if (getCurrentMember().getId().equals(cartWriterId)) {
                return true;
            }
        }

        // 소통해요 작성자가 맞는지 필터링
        if (comment instanceof CommunityComment communityComment) {
            Long communityWriterId = communityComment.getCommunity().getMember().getId();
            if (getCurrentMember().getId().equals(communityWriterId)) {
                return true;
            }
        }

        return false;
    }
}
