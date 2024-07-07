package com.api.ttoklip.domain.town.community.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CommunityEditReq {

    @Schema(type = "string", description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    @Size(max = 500)
    public String title;

    @Schema(type = "string", description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    @Size(max = 5000)
    public String content;

    @Schema(description = "게시글에 첨부할 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            format = "binary")
    public List<MultipartFile> addImages;

    @Schema(description = "삭제할 이미지의 ID 리스트", example = "[1, 2, 3]")
    public List<Long> deleteImageIds;

    @Schema(description = "게시글에 첨부할 URL. URL은 string이며 리스트로 여러개의 url을 사용할 수 있습니다, 클릭 시 글을 자유롭게 이동할 수 있습니다.", example = "[\"https://www.example.com/recipe\", \"https://www.example.com/tips\"]")
    public List<String> url;

}
