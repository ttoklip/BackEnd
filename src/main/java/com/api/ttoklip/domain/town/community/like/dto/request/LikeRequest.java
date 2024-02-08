package com.api.ttoklip.domain.town.community.like.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequest {

    @Schema(description = "멤버 아이디", example = "1")
    @NotNull
    private Long memberId;
}
