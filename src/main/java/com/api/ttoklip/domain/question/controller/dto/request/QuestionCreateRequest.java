package com.api.ttoklip.domain.question.controller.dto.request;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.PostRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class QuestionCreateRequest implements PostRequest {

    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    @Size(max = 500)
    public String title;

    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    @Size(max = 5000)
    public String content;

    @Schema(description = "카테고리입니다. HOUSEWORK, COOKING, SAFE_LIVING, WELFARE_POLICY 이 중 하나로 요청해야합니다.", example = "HOUSEWORK", allowableValues = {
            "HOUSEWORK", "COOKING", "SAFE_LIVING", "WELFARE_POLICY"})
    @NotEmpty
    public String category;

    @Schema(description = "게시글에 첨부할 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            format = "binary")
    public List<MultipartFile> images;

    public Category getCategory() {
        // 문자열을 enum으로 변환
        return Category.findCategoryByValue(category);
    }

}
