package com.api.term.presentation;

import com.api.global.util.TimeUtil;
import com.domain.term.domain.Term;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TermAdminResponse(Long termId, String title, String content, String createdAt) {
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
