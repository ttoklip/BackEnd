package com.api.profile.presentation;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ProfileWebCreate(
        String street,
        String nickname,
        List<String> categories,
        MultipartFile profileImage,

        @Min(0) @Max(99)
        int independentYear,

        @Min(0) @Max(11)
        int independentMonth
) {

    private void validCategorySize() {
        if (categories != null && categories.size() > 3) {
            throw new ApiException(ErrorType.INVALID_CATEGORIES_SIZE);
        }
    }

    public List<Category> getCategories() {
        validCategorySize();
        return categories.stream()
                .map(Category::findCategoryByValue)
                .toList();
    }
}
