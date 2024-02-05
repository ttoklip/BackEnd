package com.api.ttoklip.domain.town.cart.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CartCreateRequest {

    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    @Size(max = 50)
    public String title;

    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    @Size(max = 500)
    public String content;

    @Schema(description = "총 가격", example = "총 가격 예시")
    @NotEmpty
    public Long totalPrice;

    @Schema(description = "거래희망 장소", example = "거래희망 장소 예시")
    @NotEmpty
    @Size(max = 50)
    public String location;

    @Schema(description = "오픈채팅방 링크", example = "오픈채팅방 링크 예시")
    @NotEmpty
    @Size(max = 50)
    public String chatUrl;

    @Schema(description = "공구 최대 인원", example = "공구 최대 인원 예시")
    @NotEmpty
    public Long party;

    @Schema(description = "게시글에 첨부할 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            format = "binary")
    public List<MultipartFile> images;

    @Schema(description = "게시글에 첨부할 상품관련 URL. URL은 string이며 리스트로 여러개의 url을 사용할 수 있습니다, 클릭 시 글을 자유롭게 이동할 수 있습니다.",
            example = "[\"https://www.example.com/cart\"]")
    public List<String> itemUrls;

}
