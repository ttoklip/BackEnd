package com.api.auth.local.presentation;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record LocalMemberWebCreate(
        @Schema(description = "직접 로그인 ID", example = "ttok123@naver.com")
        String email,

        @Schema(description = "비밀번호", example = "asdf1234!")
        String password,

        @Schema(description = "이름", example = "민희진")
        String originName,

        @Schema(description = "닉네임", example = "별명별명")
        String nickname,

        @Schema(description = "독립 경력(년)", example = "2")
        int independentYear,

        @Schema(description = "독립 경력(월)", example = "6")
        int independentMonth,

        @Schema(description = "주소", example = "경기도 안산시 상록구")
        String street,

        @Schema(description = "이용약관 동의 여부", example = "true")
        boolean agreeTermsOfService,

        @Schema(description = "개인정보처리방침 동의 여부", example = "true")
        boolean agreePrivacyPolicy,

        @Schema(description = "위치기반서비스 동의 여부", example = "true")
        boolean agreeLocationService,

        List<String> categories,

        MultipartFile profileImage
) {


    public List<Category> getCategories() {
        validCategorySize();
        return categories.stream()
                .map(Category::findCategoryByValue)
                .toList();
    }

    private void validCategorySize() {
        if (categories != null && categories.size() > 3) {
            throw new ApiException(ErrorType.INVALID_CATEGORIES_SIZE);
        }
    }

}
