package com.api.ttoklip.domain.town.cart.post.dto.request;

import com.api.ttoklip.domain.common.PostCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CartCreateRequest implements PostCreateRequest {

    @Schema(description = "게시글 제목", example = "게시글 제목 예시")
    @NotEmpty
    @Size(max = 500)
    public String title;

    @Schema(description = "게시글 내용", example = "게시글 내용 예시")
    @NotEmpty
    @Size(max = 5000)
    public String content;

    @Schema(description = "총 가격", example = "총 가격 예시")
    @NotNull
    public Long totalPrice;

    @Schema(description = "거래희망 장소", example = "거래희망 장소 예시")
    @NotEmpty
    @Size(max = 500)
    public String location;

    @Schema(description = "오픈채팅방 링크", example = "오픈채팅방 링크 예시")
    @NotEmpty
    @Size(max = 500)
    public String chatUrl;

//    @Schema(description = "공구 현재 참여 인원", example = "3")
//    @NotNull
//    public Long partyCnt;

    @Schema(description = "공구 최대 인원", example = "10")
    @NotNull
    public Long partyMax;

    @Schema(description = "게시글에 첨부할 이미지 파일. 파일 형식은 binary이며, 지원되는 이미지 형식은 JPEG, PNG 등입니다.",
            format = "binary")
    public List<MultipartFile> images;

    @Schema(description = "게시글에 첨부할 상품관련 URL. URL은 string이며 리스트로 여러개의 url을 사용할 수 있습니다, 클릭 시 글을 자유롭게 이동할 수 있습니다.",
            example = "[\"https://www.example.com/cart\"]")
    public List<String> itemUrls;

}
