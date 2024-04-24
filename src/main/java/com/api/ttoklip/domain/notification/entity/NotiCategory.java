package com.api.ttoklip.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotiCategory {

    HONEY_TIP_SCRAP("꿀팁 공유해요", "누군가가 글을 스크랩 했어요", true, false),
    HONEY_TIP_LIKE("꿀팁 공유해요", "누군가에게 글이 도움이 되었대요", true, false),
    HONEY_TIP_COMMENT("꿀팁 공유해요", "누군가가 댓글을 남겼어요", true, false),
    HONEY_TIP_CHILD_COMMENT("꿀팁 공유해요", "누군가 답글을 남겼어요", true, true),

    QUESTION_HELPFUL("질문해요", "누군가에게 남겨준 댓글이 도움이 되었대요", true, false),
    QUESTION_COMMENT("질문해요", "누군가가 댓글을 남겼어요", true, false),
    QUESTION_CHILD_COMMENT("질문해요", "누군가가 답글을 남겼어요", true, true),

    NEWS_LETTER_CHILD_COMMENT("뉴스레터", "누군가가 답글을 남겼어요", false, true),

    OUR_TOWN_SCRAP("우리동네 스크랩", "누군가가 글을 스크랩 했어요", true, false),
    OUR_TOWN_COMMENT("우리동네 댓글", "누군가가 댓글을 남겼어요", true, false),
    OUR_TOWN_CHILD_COMMENT("우리동네 답글", "누군가가 답글을 남겼어요", true, true),
    OUR_TOWN_TOGETHER("우리동네 함께해요", "누군가가 함께하기에 참여했어요", true, false),

    PROFILE_LIKE("프로필 좋아요", "누군가가 내 프로필에 좋아요를 눌렀어요", false, false),

    BAD_TYPE_NOTIFICATION("알림 타입 없음", "해당 알림이 반환되면 안됩니다.", false, false),
    ;

    private final String title;
    private final String text;
    private final boolean notifyPostWriter;     // 해당 게시글 작성자에게 알릴 여부
    private final boolean notifyCommentWriter;  // 해당 댓글 작성자에게 알릴 여부
}
