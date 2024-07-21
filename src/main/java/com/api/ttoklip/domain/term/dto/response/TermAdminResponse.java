package com.api.ttoklip.domain.term.dto.response;


import com.api.ttoklip.domain.term.domain.Term;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TermAdminResponse {
    @Schema(description = "약관 ID")
    private Long termId;

    @Schema(description = "약관 제목")
    private String title;

    @Schema(description = "약관 내용")
    private String content;

    @Schema(description = "약관 작성일자")
    private String createdAt;

    public static TermAdminResponse of(final Term term) {

        String formattedCreatedDate = getFormattedCreatedDate(term);

        return TermAdminResponse.builder()
                .termId(term.getId())
                .title(term.getTitle())
                .content(term.getContent())
                .createdAt(formattedCreatedDate)
                .build();
    }

    private static String getFormattedCreatedDate(final Term term) {
        LocalDateTime createdDate = term.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }
}
