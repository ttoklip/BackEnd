package com.domain.common.vo;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {

    HOUSEWORK("집안일", 1),
    RECIPE("레시피", 2),
    SAFE_LIVING("안전한 생활", 3),
    WELFARE_POLICY("복지 정책", 4);

    private final String name;
    private final int code;

    public static Category findCategoryByValue(final String value) {
        try {
            return Category.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorType.CATEGORY_NOT_FOUND);
        }
    }

    /*
    public static Category findCategoryByName(final String name) {
        for (Category category : Category.values()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        throw new ApiException(ErrorType.CATEGORY_NOT_FOUND);
    }

    public static Category findCategoryByCode(final int code) {
        for (Category category : Category.values()) {
            if (category.getCode() == code) {
                return category;
            }
        }
        throw new ApiException(ErrorType.CATEGORY_NOT_FOUND);
    }

     */
}
