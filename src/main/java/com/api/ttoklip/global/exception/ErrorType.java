package com.api.ttoklip.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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


    // ------------------------------------------ Report ------------------------------------------
    REPORT_NOT_FOUND(NOT_FOUND, "Report_4040", "신고 타입을 찾을 수 없습니다."),


    // ------------------------------------------ Question ------------------------------------------
    QUESTION_NOT_FOUND(NOT_FOUND, "Question_4040", "질문해요를 찾을 수 없습니다."),


    // ------------------------------------------ Community ------------------------------------------
    COMMUNITY_NOT_FOUNT(NOT_FOUND, "Community_4040", "소통해요를 찾을 수 없습니다."),


    // ------------------------------------------ Comment ------------------------------------------
    COMMENT_NOT_FOUND(NOT_FOUND, "Comment_4040", "댓글을 찾을 수 없습니다.");

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
