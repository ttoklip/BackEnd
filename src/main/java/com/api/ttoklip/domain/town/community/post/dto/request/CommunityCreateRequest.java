package com.api.ttoklip.domain.town.community.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommunityCreateRequest {

    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    @Size(max = 50)
    public String title;

    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    @Size(max = 500)
    public String content;

    @Schema(description = "게시글에 첨부할 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            format = "binary")
    public List<MultipartFile> images;

}
