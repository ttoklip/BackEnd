package com.api.ttoklip.domain.town;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public enum TownCriteria {

    CITY("시"),
    DISTRICT("구"),
    TOWN("동"),

    ;

    private final String Criteria;

    public static TownCriteria findTownCriteriaByValue(final String value) {
        try {
            return TownCriteria.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid value for TownCriteria: {}", value);
            throw new ApiException(ErrorType.INVALID_SORT_TYPE);
        }
    }
}
