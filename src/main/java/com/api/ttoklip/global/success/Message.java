package com.api.ttoklip.global.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {

    @Schema(type = "string", example = "메시지 문구를 출력합니다.", description = "메시지 입니다.")
    private String message;

    private static final String COMMENT = "댓글";
    private static final String POST = "게시글";
    private static final String CREATE = "생성";
    private static final String EDIT = "수정";
    private static final String DELETE = "삭제";
    private static final String REPORT = "신고";
    private static final String LIKE = "좋아요";


    private static <T> Message actionSuccess(Class<T> itemType, Long itemId, String dataType, String action) {
        String typeName = itemType.getSimpleName(); // 클래스의 단순 이름을 가져옴
        String message = String.format("%s Type의 %d번째 %s을(를) %s했습니다.", typeName, itemId, dataType, action);
        return Message.builder()
                .message(message)
                .build();
    }

    public static <T> Message editCommentSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, COMMENT, EDIT);
    }

    public static <T> Message editPostSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, POST, EDIT);
    }

    public static <T> Message deleteCommentSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, COMMENT, DELETE);
    }

    public static <T> Message deletePostSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, POST, DELETE);
    }

    public static <T> Message registerCommentSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, COMMENT, CREATE);
    }

    public static <T> Message registerPostSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, POST, CREATE);
    }

    public static <T> Message reportCommentSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, COMMENT, REPORT);
    }

    public static <T> Message reportPostSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, POST, REPORT);
    }

    public static <T> Message likePostSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, LIKE, CREATE);
    }

    public static <T> Message likePostCancel(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, LIKE, DELETE);
    }

    public static Message insertPrivacy() {
        return Message.builder()
                .message("회원가입 후 개인정보를 추가했습니다.")
                .build();
    }

    public static Message validNickname() {
        return Message.builder()
                .message("닉네임 중복 확인에 통과하였습니다.")
                .build();
    }
}
