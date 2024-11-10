package com.api.ttoklip.domain.mypage.dto.request;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@AllArgsConstructor
public class ProfileEditRequest {
    private String street;
    private String nickname;
    private List<String> categories;

    @NotNull(message = "프로필 이미지는 필수입니다.")
    private MultipartFile profileImage;

    @Min(0)
    @Max(99)
    private int independentYear;

    @Min(0)
    @Max(11)
    private int independentMonth;

    public List<Category> getCategories() {
        validCategorySize();
        return categories.stream()
                .map(Category::findCategoryByValue)
                .toList();
    }

    private void validCategorySize() {
        if (categories.size() > 3) {
            throw new ApiException(ErrorType.INVALID_CATEGORIES_SIZE);
        }
    }
}
