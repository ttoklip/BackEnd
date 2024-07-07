package com.api.ttoklip.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorType {

    /**
     * Error Message Convention
     * <p>
     * name : _(head) + Error Name status : HttpStatus
     * <p>
     * errorCode : 400번 오류인 상황이 여러개 올텐데, 4001, 4002, 4003.. 이런식으로 설정 (해당 오류들은 MEMBER 와 관련된 400 오류들) ex) Member Error,
     * Http Status Code: 400 -> MEMBER_4000
     * <p>
     * message : 사람이 알아볼 수 있도록 작성 ex) "인증이 필요합니다."
     */

    // ------------------------------------------ S3 ------------------------------------------
    EXCEEDING_FILE_COUNT(BAD_REQUEST, "S4001", "사진 개수가 너무 많습니다."),
    EXCEEDING_FILE_SIZE(BAD_REQUEST, "S4002", "사진 용량이 너무 큽니다."),
    S3_UPLOAD(INTERNAL_SERVER_ERROR, "S5001", "서버오류, S3 사진 업로드 에러입니다."),
    S3_CONNECT(INTERNAL_SERVER_ERROR, "S5002", "서버오류, S3 연결 에러입니다."),
    S3_CONVERT(INTERNAL_SERVER_ERROR, "S5003", "서버오류, S3 변환 에러입니다."),

    // ------------------------------------------ Category ------------------------------------------
    CATEGORY_NOT_FOUND(NOT_FOUND, "Category_4040", "카테고리를 찾을 수 없습니다."),
    CATEGORY_NOT_EXISTS(NOT_FOUND, "Category_4041", "카테고리는 필수로 선택해야합니다."),


    // ------------------------------------------ Report ------------------------------------------
    REPORT_NOT_FOUND(NOT_FOUND, "Report_4040", "신고 타입을 찾을 수 없습니다."),


    // ------------------------------------------ Question ------------------------------------------
    QUESTION_NOT_FOUND(NOT_FOUND, "Question_4040", "질문해요를 찾을 수 없습니다."),
    // ------------------------------------------ Notice ------------------------------------------
    NOTICE_NOT_FOUND(NOT_FOUND, "Notice_4040", "공지사항을 찾을 수 없습니다."),
    // ------------------------------------------ Notice ------------------------------------------
    TERM_NOT_FOUND(NOT_FOUND, "Term_4040", "약관을 찾을 수 없습니다."),

    // ------------------------------------------ Cart ------------------------------------------
    CART_NOT_FOUND(NOT_FOUND, "Cart_4040", "함께해요를 찾을 수 없습니다."),
    PARTICIPANT_EXCEEDED(BAD_REQUEST, "Cart_4000", "참가자 수가 공구 인원의 최대 허용 인원을 초과하였습니다."),
    ALREADY_PARTICIPATED(BAD_REQUEST, "Cart_4000", "이미 공구에 참여 중입니다."),
    NOT_PARTICIPATED(BAD_REQUEST, "Cart_4000", "참여 중이 아니라 취소할 수 없습니다."),


    // ------------------------------------------ Community ------------------------------------------
    COMMUNITY_NOT_FOUND(NOT_FOUND, "Community_4040", "소통해요를 찾을 수 없습니다."),


    // ------------------------------------------ Comment ------------------------------------------
    COMMENT_NOT_FOUND(NOT_FOUND, "Comment_4040", "댓글을 찾을 수 없습니다."),
    COMMENT_IS_DISABLE(BAD_REQUEST, "Comment_4041", "삭제된 댓글에는 답글을 작성할 수 없습니다."),
    HONEY_TIP_COMMENT_NOT_FOUND(NOT_FOUND, "Comment_4042", "꿀팁공유해요의 댓글을 찾을 수 없습니다."),
    QUESTION_COMMENT_NOT_FOUND(NOT_FOUND, "Comment_4043", "질문해요의 댓글을 찾을 수 없습니다."),
    CART_COMMENT_NOT_FOUND(NOT_FOUND, "Comment_4043", "함께해요의 댓글을 찾을 수 없습니다."),
    COMMUNITY_COMMENT_NOT_FOUND(NOT_FOUND, "Comment_4043", "소통해요의 댓글을 찾을 수 없습니다."),

    // ------------------------------------------ HoneyTip ------------------------------------------
    HONEY_TIP_NOT_FOUND(NOT_FOUND, "HoneyTip_4040", "꿀팁공유해요를 찾을 수 없습니다."),


    // ------------------------------------------ Newsletter ------------------------------------------
    NEWSLETTER_NOT_FOUND(NOT_FOUND, "Newsletter_4040", "뉴스레터를 찾을 수 없습니다."),


    // ---------------------------------------- JWT TOKEN ----------------------------------------
    _JWT_PARSING_ERROR(BAD_REQUEST, "JWT_4001", "JWT Token이 올바르지 않습니다."),
    _JWT_EXPIRED(UNAUTHORIZED, "JWT_4010", "Jwt Token의 유효 기간이 만료되었습니다."),
    _JWT_NOT_FOUND(UNAUTHORIZED, "JWT_4010", "Jwt Token을 포함하셔야합니다."),


    // ------------------------------------------ Auth ------------------------------------------
    OAUTH_INVALID_PROVIDER(INTERNAL_SERVER_ERROR, "OAUTH_5000", "올바르지 않은 Provider입니다."),

    KAKAO_NOTFOUND_NAME(INTERNAL_SERVER_ERROR, "OAUTH_5001", "Kakao로부터 name을 받을 수 없습니다."),
    KAKAO_NOTFOUND_EMAIL(INTERNAL_SERVER_ERROR, "OAUTH_5002", "Kakao로부터 email을 받을 수 없습니다."),
    KAKAO_NOTFOUND_PROFILE_IMAGE_URL(INTERNAL_SERVER_ERROR, "OAUTH_5003", "Kakao로부터 profile image url을 받을 수 없습니다."),
    KAKAO_TOKEN_INVALID(UNAUTHORIZED, "OAUTH_4010", "Kakao 제공자에 요청하였지만, Token이 잘못되었거나 만료되었습니다."),

    NAVER_NOTFOUND_NAME(INTERNAL_SERVER_ERROR, "OAUTH_5004", "Naver로부터 name을 받을 수 없습니다."),
    NAVER_NOTFOUND_EMAIL(INTERNAL_SERVER_ERROR, "OAUTH_5005", "Naver로부터 email을 받을 수 없습니다."),
    NAVER_NOTFOUND_PROFILE_IMAGE_URL(INTERNAL_SERVER_ERROR, "OAUTH_5006", "Naver로부터 profile image url을 받을 수 없습니다."),
    NAVER_TOKEN_INVALID(UNAUTHORIZED, "OAUTH_4011", "Naver 제공자에 요청하였지만, Token이 잘못되었거나 만료되었습니다."),


    // ------------------------------------------ USER ------------------------------------------
    _USER_NOT_FOUND_BY_TOKEN(NOT_FOUND, "USER_4040", "제공된 토큰으로 사용자를 찾을 수 없습니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "USER_4010", "로그인되지 않은 상태입니다."),
    _USER_NOT_FOUND_DB(NOT_FOUND, "USER_4041", "존재하지 않는 회원입니다."),
    _USER_FCM_TOKEN_NOT_FOUND(NOT_FOUND, "USER_4042", "FCM 토큰이 없습니다."),
    _USER_ALREADY_KAKAO_PLATFORM(BAD_REQUEST, "USER_4043", "이미 카카오로 가입된 회원입니다."),
    _USER_ALREADY_NAVER_PLATFORM(BAD_REQUEST, "USER_4043", "이미 네이버로 가입된 회원입니다."),


    // ------------------------------------------ LIKE ------------------------------------------
    LIKE_NOT_FOUND(NOT_FOUND, "LIKE_4041", "좋아요가 존재하지 않습니다."),


    // ------------------------------------------ SCRAP ------------------------------------------
    SCRAP_NOT_FOUND(NOT_FOUND, "SCRAP_4041", "스크랩이 존재하지 않습니다."),


    // ------------------------------------------ AUTHORIZATION ------------------------------------------
    UNAUTHORIZED_EDIT_POST(FORBIDDEN, "AUTH_4031", "게시글의 작성자만 수정할 수 있습니다."),
    UNAUTHORIZED_DELETE_COMMENT(FORBIDDEN, "AUTH_4032", "댓글의 작성자만 삭제할 수 있습니다."),
    UNAUTHORIZED_CANCEL_LIKE(FORBIDDEN, "AUTH_4033", "좋아요한 사용자만 본인의 좋아요를 취소할 수 있습니다."),


    // ------------------------------------------ Privacy ------------------------------------------
    INVALID_CATEGORIES_SIZE(BAD_REQUEST, "Privacy_4041", "회원가입시 카테고리는 최대 3개까지 선택가능합니다."),
    ALREADY_EXISTS_NICKNAME(BAD_REQUEST, "Privacy_4042", "이미 사용중인 닉네임입니다."),
    LOCATION_NOT_FOUND(BAD_REQUEST, "Privacy_4043", "위도와 경고는 필수입니다."),


    // ------------------------------------------ Query ------------------------------------------
    INVALID_SORT_TYPE(BAD_REQUEST, "Sort_4041", "지원하지 않는 조회 타입입니다."),

    // ------------------------------------------ Infra ------------------------------------------
    _NOT_SEND_ABLE(INTERNAL_SERVER_ERROR, "Firebase_5001", "Firebase를 통해 알림을 전송할 수 없습니다."),
    _NOT_SEND_CONDITION(INTERNAL_SERVER_ERROR, "Firebase_5002", "FCM TOKEN 혹은 TOPIC이 잘못됐습니다."),


    // ------------------------------------------ Notification ------------------------------------------
    BAD_CATEGORY_NOTIFICATION(BAD_REQUEST, "Notification_4001", "지원하지 않는 알림 카테고리입니다."),
    _BAD_CATEGORY_NOTIFICATION_TYPE(INTERNAL_SERVER_ERROR, "Notification_5001", "내부에서 공지타입을 찾는데 오류가 발생했습니다."),


    // ------------------------------------------ ProfileLike ------------------------------------------
    Profile_LIKE_MYSELF(BAD_REQUEST, "ProfileLike_4001", "본인 프로필에 좋아요할 수 없습니다."),
    Profile_NOT_FOUND(NOT_FOUND, "Profile_4041", "프로필을 찾을 수 없습니다."),


    // ------------------------------------------ Image ------------------------------------------
    DELETE_INVALID_IMAGE_IDS(BAD_REQUEST, "Image_4041", "삭제하려는 이미지 ID가 DB에 존재하지 않는 게 있습니다."),
    INVALID_DELETE_IMAGE_OWNER(FORBIDDEN, "Image_4031", "이미지들의 오너가 아닙니다."),

    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    ErrorType(final HttpStatus status, final String errorCode, final String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
