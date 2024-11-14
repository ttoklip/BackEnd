package com.domain.report.domain;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {

    FISHING_DUPLICATE_SPAM("낚시 / 중복 / 도배성 게시물"),
    COMMERCIAL_ADVERTISING("상업적 광고 / 홍보 글"),
    INAPPROPRIATE_CONTENT("선정적 / 불쾌함이 느껴지는 부적절한 글"),
    ABUSE("비방 / 욕설/ 혐오 표현이 사용된 글"),
    RELIGIOUS_PROSELYTIZING("종교 / 포교 관련 글"),
    INAPPROPRIATE_FOR_FORUM("게시판 성격에 부적절"),
    LEAK_IMPERSONATION_FRAUD("유출 / 사칭 / 사기");

    private final String description;

    public static ReportType findReportTypeByValue(final String value) {
        try {
            return ReportType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorType.REPORT_NOT_FOUND);
        }
    }

}
