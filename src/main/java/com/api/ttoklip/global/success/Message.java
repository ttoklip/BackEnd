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

    public static final String COMMENT = "댓글";
    public static final String LIKE = "좋아요";
    public static final String SCRAP = "스크랩";
    private static final String POST = "게시글";
    private static final String PARTY = "공구 참여";
    private static final String CREATE = "생성";
    private static final String STATUS = "상태";
    private static final String EDIT = "수정";
    private static final String DELETE = "삭제";
    private static final String REPORT = "신고";
    @Schema(type = "string", example = "메시지 문구를 출력합니다.", description = "메시지 입니다.")
    private String message;

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

    public static Message reportMemberSuccess(String reportedMemberNickName) {
        String message = String.format("닉네임 %s인 회원을 신고하였습니다.", reportedMemberNickName);

        return Message.builder()
                .message(message)
                .build();
    }

    public static <T> Message likePostSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, LIKE, CREATE);
    }

    public static <T> Message likePostCancel(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, LIKE, DELETE);
    }

    public static <T> Message editStatusSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, STATUS, EDIT);
    }

    public static <T> Message scrapPostSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, SCRAP, CREATE);
    }

    public static <T> Message scrapPostCancel(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, SCRAP, DELETE);
    }

    public static <T> Message addParticipantSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, PARTY, CREATE);
    }

    public static <T> Message removeParticipantSuccess(Class<T> itemType, Long itemId) {
        return actionSuccess(itemType, itemId, PARTY, DELETE);
    }

    public static Message insertPrivacy() {
        return Message.builder()
                .message("회원가입 후 개인정보를 추가했습니다.")
                .build();
    }

    public static Message editProfile() {
        return Message.builder()
                .message("개인정보를 수정했습니다.")
                .build();
    }

    public static Message validNickname() {
        return Message.builder()
                .message("닉네임 중복 확인에 통과하였습니다.")
                .build();
    }

    public static Message validId() {
        return Message.builder()
                .message("아이디 중복 확인에 통과하였습니다.")
                .build();
    }

    public static Message activateUser() {
        return Message.builder()
                .message("차단해제 하였습니다.")
                .build();
    }

    public static Message registerUser() {
        return Message.builder()
                .message("회원가입 성공하였습니다.")
                .build();
    }

    public static Message sendEmail() {
        return Message.builder()
                .message("인증코드가 발송되었습니다.")
                .build();
    }

    public static Message verifyCodeSuccess() {
        return Message.builder()
                .message("인증이 완료되었습니다.")
                .build();
    }

    public static Message verifyCodeFail() {
        return Message.builder()
                .message("인증 실패하셨습니다.")
                .build();
    }

    public static Message updateFCM() {
        return Message.builder()
                .message("FCM Token이 생성되었습니다.")
                .build();
    }

    public static Message sendAlarmSuccess(final String email) {
        return Message.builder()
                .message("target " + email + "님에게 알림이 성공적으러로 전송되었습니다")
                .build();
    }

    public static Message registerProfileLike(final Long targetMemberId) {
        return Message.builder()
                .message("target " + targetMemberId + "번 회원 프로필에 좋아요를 추가했습니다")
                .build();
    }

    public static Message cancelProfileLike(final Long targetMemberId) {
        return Message.builder()
                .message("target " + targetMemberId + "번 회원 프로필에 좋아요를 취소했습니다")
                .build();
    }
}
