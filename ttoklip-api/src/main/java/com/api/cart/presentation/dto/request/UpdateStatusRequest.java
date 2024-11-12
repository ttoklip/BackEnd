package com.api.cart.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequest {

    @Schema(description = "변경하려는 게시글 상태")
    private String status;

    public String getStatus() {
        return status;
    }
}
