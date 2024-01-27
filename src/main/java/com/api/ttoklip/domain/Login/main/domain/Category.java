package com.api.ttoklip.domain.Login.main.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    HOUSEWORK("집안일", 1),
    RECIPE("레시피", 2),
    SAFE_LIVING("안전한 생활", 3),
    WELFARE_POLICY("복지 정책", 4);


    private final String name;
    private final int code;

}
