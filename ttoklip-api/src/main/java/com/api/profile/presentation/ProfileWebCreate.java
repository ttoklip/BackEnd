package com.api.profile.presentation;

import com.common.base.Filterable;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ProfileWebCreate(

        @NotBlank(message = "street 필드는 필수 입력 값입니다.")
        String street,

        @NotBlank(message = "nickname 필드는 필수 입력 값입니다.")
        String nickname,

        @NotNull(message = "categories 필드는 필수 입력 값입니다.")
        @NotEmpty(message = "categories 필드는 최소 1개 이상의 항목이 필요합니다.")
        List<String> categories,

        @NotNull(message = "profileImage 필드는 필수 입력 값입니다.")
        MultipartFile profileImage,

        @Min(value = 0, message = "independentYear는 0 이상이어야 합니다.")
        @Max(value = 99, message = "independentYear는 99 이하이어야 합니다.")
        int independentYear,

        @Min(value = 0, message = "independentMonth는 0 이상이어야 합니다.")
        @Max(value = 11, message = "independentMonth는 11 이하이어야 합니다.")
        int independentMonth

) implements Filterable {

    private void validCategorySize() {
        if (categories.size() > 3) {
            throw new ApiException(ErrorType.INVALID_CATEGORIES_SIZE);
        }
    }

    public List<Category> getCategories() {
        validCategorySize();
        return categories.stream()
                .map(Category::findCategoryByValue)
                .toList();
    }

    @Override
    public String getFilterContent() {
        return nickname;
    }
}
