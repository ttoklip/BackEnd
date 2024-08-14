package com.api.ttoklip.domain.town.community.post.dto.request;

import com.api.ttoklip.domain.common.PostCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CommunityCreateRequest implements PostCreateRequest {

    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    @Size(max = 500)
    public String title;

    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    @Size(max = 5000)
    public String content;

    @Schema(description = "게시글에 첨부할 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            format = "binary")
    public List<MultipartFile> images;

}
