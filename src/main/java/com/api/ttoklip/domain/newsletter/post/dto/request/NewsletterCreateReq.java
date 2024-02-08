package com.api.ttoklip.domain.newsletter.post.dto.request;

import com.api.ttoklip.domain.common.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class NewsletterCreateReq {

    @Schema(type = "string", description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    @Size(max = 50)
    public String title;

    @Schema(type = "string", description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    @Size(max = 500)
    public String content;

    @Schema(description = "카테고리입니다. HOUSEWORK, RECIPE, SAFE_LIVING, WELFARE_POLICY 이 중 하나로 요청해야합니다.", example = "HOUSEWORK", allowableValues = {
            "HOUSEWORK", "RECIPE", "SAFE_LIVING", "WELFARE_POLICY"})
    @NotNull
    public String category;

    @Schema(description = "게시글에 첨부할 대표 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            format = "binary")
    @NotNull
    public MultipartFile mainImage;

    @Schema(description = "게시글에 첨부할 서브 이미지 파일들. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            format = "binary")
    public List<MultipartFile> subImages;

    @Schema(description = "게시글에 첨부할 URL. URL은 string이며 리스트로 여러개의 url을 사용할 수 있습니다, 클릭 시 글을 자유롭게 이동할 수 있습니다.", example = "[\"https://www.example.com/recipe\", \"https://www.example.com/tips\"]")
    public List<String> url;

    public Category getCategory() {
        // 문자열을 enum으로 변환
        return Category.findCategoryByValue(category);
    }
}
