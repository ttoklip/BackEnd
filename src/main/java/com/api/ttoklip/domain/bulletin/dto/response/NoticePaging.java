package com.api.ttoklip.domain.bulletin.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record NoticePaging(List<NoticeSingleResponse> notices, Integer totalPage,
                           Long totalElements, Boolean isFirst, Boolean isLast) {

    public static NoticePaging of(List<NoticeSingleResponse> notices, Integer totalPage,
                                  Long totalElements, Boolean isFirst, Boolean isLast) {
        return NoticePaging.builder()
                .notices(notices)
                .totalPage(totalPage)
                .totalElements(totalElements)
                .isFirst(isFirst)
                .isLast(isLast)
                .build();
    }
}
