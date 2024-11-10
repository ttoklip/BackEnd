package com.domain.member.domain.vo;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {

    KAKAO("kakao"),
    NAVER("naver"),
    LOCAL("local"),
    ;

    private final String type;

    public static Provider from(final String value) {
        return Arrays.stream(Provider.values())
                .filter(provider -> provider.type.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorType.INVALD_PROVIDER_TYPE));
    }
}
