package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationRegistry {

    public NotiCategory determineNotiCategory(final String className, final String methodName) {
        // 클래스 이름과 메서드 이름을 검사하여 알림 종류를 판단
        if ("HoneyTipScrapService".equals(className) && "register".equals(methodName)) {
            return NotiCategory.HONEY_TIP_SCRAP; // 스크랩 생성에 대한 알림
        }

        if ("HoneyTipLikeService".equals(className) && "register".equals(methodName)) {
            return NotiCategory.HONEY_TIP_LIKE; // 스크랩 생성에 대한 알림
        }

        if ("QuestionCommentService".equals(className) && "registerLike".equals(methodName)) {
            return NotiCategory.QUESTION_COMMENT_LIKE;
        }

        if ("CommunityPostService".equals(className) && "registerScrap".equals(methodName)) {
            return NotiCategory.OUR_TOWN_SCRAP;
        }

        if ("CartPostService".equals(className) && "addParticipant".equals(methodName)) {
            return NotiCategory.OUR_TOWN_TOGETHER;
        }

        // ToDo 알림 종류 판단 로직
        return NotiCategory.BAD_TYPE_NOTIFICATION;
    }

    public NotiCategory determineCommentNotiCategory(final Comment comment) {
        if (comment instanceof HoneyTipComment) {
            return whenHoneyTip((HoneyTipComment) comment);
        }

        if (comment instanceof QuestionComment) {
            return whenQuestion((QuestionComment) comment);
        }

        if (comment instanceof NewsletterComment) {
            return whenNewsletter((NewsletterComment) comment);
        }

        if (comment instanceof CartComment) {
            return whenCart((CartComment) comment);
        }

        log.error("[NOTICE] 해당에 없는 댓글 호출");
        return NotiCategory.BAD_TYPE_NOTIFICATION;
    }

    private NotiCategory whenHoneyTip(final HoneyTipComment comment) {
        if (comment.getParent() == null) {
            return NotiCategory.HONEY_TIP_COMMENT;
        }
        return NotiCategory.HONEY_TIP_CHILD_COMMENT;
    }

    private NotiCategory whenQuestion(final QuestionComment comment) {
        if (comment.getParent() == null) {
            return NotiCategory.QUESTION_COMMENT;
        }
        return NotiCategory.QUESTION_CHILD_COMMENT;
    }

    // 뉴스레터는 관리자가 작성하는 것이므로 답글만 알림이 올 수 있음
    private NotiCategory whenNewsletter(final NewsletterComment comment) {
        if (comment.getParent() != null) {
            return NotiCategory.NEWS_LETTER_CHILD_COMMENT;
        }
        log.info("[NOTICE] 뉴스레터는 관리자가 작성하기 때문에 답글만 알림이 올 수 있습니다. 댓글 알림 x");
        return NotiCategory.BAD_TYPE_NOTIFICATION;
    }

    private NotiCategory whenCart(final CartComment comment) {
        if (comment.getParent() == null) {
            return NotiCategory.OUR_TOWN_COMMENT;
        }
        return NotiCategory.OUR_TOWN_CHILD_COMMENT;
    }
}
