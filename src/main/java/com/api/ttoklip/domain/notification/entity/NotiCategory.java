package com.api.ttoklip.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotiCategory {

    HONEY_TIP_SCRAP("꿀팁 공유해요", "누군가가 글을 스크랩 했어요"),
    HONEY_TIP_HELPFUL("꿀팁 공유해요", "누군가에게 글이 도움이 되었대요"),
    HONEY_TIP_COMMENT("꿀팁 공유해요", "누군가가 댓글을 남겼어요"),
    HONEY_TIP_CHILD_COMMENT("꿀팁 공유해요", "누군가 답글을 남겼어요"),

    QUESTION_HELPFUL("질문해요", "누군가에게 남겨준 댓글이 도움이 되었대요"),
    QUESTION_COMMENT("질문해요", "누군가가 댓글을 남겼어요"),
    QUESTION_CHILD_COMMENT("질문해요", "누군가가 답글을 남겼어요"),

    NEWS_LETTER_CHILD_COMMENT("뉴스레터", "누군가가 답글을 남겼어요"),

    OUR_TOWN_SCRAP("우리동네 스크랩", "누군가가 글을 스크랩 했어요"),
    OUR_TOWN_COMMENT("우리동네 댓글", "누군가가 댓글을 남겼어요"),
    OUR_TOWN_CHILD_COMMENT("우리동네 답글", "누군가가 답글을 남겼어요"),
    OUR_TOWN_TOGETHER("우리동네 함께해요", "누군가가 함께하기에 참여했어요"),

    PROFILE_LIKE("프로필 좋아요", "누군가가 내 프로필에 좋아요를 눌렀어요");

    private final String title;
    private final String text;
}
