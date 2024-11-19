package com.api.stranger.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.search.presentation.response.HoneyTipPaging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Stranger", description = "타인 프로필 관리 API")
@RequestMapping("/api/v1/stranger")
public interface StrangerControllerDocs {

    @Operation(summary = "타인 프로필 조회", description = "타인의 기본 정보인 닉네임, 동네, 레벨, 충족도를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "타인 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "StrangerConstant.strangerResponse",
                                    description = "타인의 정보 조회 성공했습니다."
                            )))})

    @GetMapping
    TtoklipResponse<StrangerResponse> getStrangerProfile(
            @RequestParam String nickname);

    @Operation(summary = "타인이 작성한 꿀팁 목록 조회", description = "타인이 작성한 꿀팁 글 목록을 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 꿀팁 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "StrangerConstant.strangerHoneyTipsResponse",
                                    description = "타 유저가 작성한 꿀팁들을 불러왔습니다."
                            )))})

    @GetMapping("/honeytip/{userId}")
    TtoklipResponse<HoneyTipPaging> strangerHoneyTip(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @PathVariable Long userId);
}
