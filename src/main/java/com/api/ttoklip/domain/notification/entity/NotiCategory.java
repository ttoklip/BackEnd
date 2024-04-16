package com.api.ttoklip.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotiCategory {

    HONEY_TIP_SCRAP("꿀팁 공유해요 스크랩"),
    HONEY_TIP_HELPFUL("꿀팁 공유해요 도움이 되었어요"),
    HONEY_TIP_COMMENT("꿀팁 공유해요 댓글"),
    HONEY_TIP_CHILD_COMMENT("꿀팁 공유해요 답글"),

    QUESTION_HELPFUL("질문해요 도움이 되었어요"),
    QUESTION_COMMENT("질문해요 댓글"),
    QUESTION_CHILD_COMMENT("질문해요 답글"),

    NEWS_LETTER_CHILD_COMMENT("뉴스레터 답글"),

    OUR_TOWN_SCRAP("우리동네 스크랩"),
    OUR_TOWN_COMMENT("우리동네 댓글"),
    OUR_TOWN_CHILD_COMMENT("우리동네 답글"),
    OUR_TOWN_TOGETHER("우리동네 함께해요"),

    PROFILE_LIKE("프로필 좋아요")
    ;

    private final String name;
}
