package com.api.community.presentation;

import com.api.common.ReportWebCreate;
import com.api.community.presentation.dto.request.CommunityWebCreate;
import com.api.community.presentation.dto.request.CommunityWebEdit;
import com.api.community.presentation.dto.response.CommunityResponse;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Community", description = "우리동네 - 소통해요 API")
@RequestMapping("/api/v1/town/comms")
public interface CommunityPostControllerDocs {

    @Operation(summary = "소통해요 게시글 생성", description = "소통해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CommunityResponseConstant.createAndDeleteCommunity",
                                    description = "소통해요 게시글이 생성되었습니다."
                            )))} )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TtoklipResponse<Message> register(@Validated @ModelAttribute CommunityWebCreate request);

    @Operation(summary = "소통해요 게시글 조회", description = "소통해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommunityResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CommunityResponseConstant.readSingleCommunity",
                                    description = "소통해요 게시글이 조회되었습니다."
                            )))} )
    @GetMapping("/{postId}")
    TtoklipResponse<CommunityResponse> getSinglePost(@PathVariable Long postId);

    @Operation(summary = "소통해요 게시글 수정", description = "소통해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CommunityResponseConstant.updateCommunity",
                                    description = "소통해요 게시글이 수정되었습니다."
                            )))} )
    @PatchMapping("/{postId}")
    TtoklipResponse<Message> edit(@PathVariable Long postId, @Validated @ModelAttribute CommunityWebEdit request);

    @Operation(summary = "소통해요 게시글 삭제", description = "소통해요 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 게시글 삭제 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CommunityResponseConstant.deleteCommunity",
                                    description = "소통해요 게시글을 삭제했습니다."
                            )))} )
    @DeleteMapping("/{postId}")
    TtoklipResponse<Message> delete(@PathVariable Long postId);

    @Operation(summary = "소통해요 게시글 신고", description = "소통해요 ID에 해당하는 게시글을 신고합니다.")
    @PostMapping("/report/{postId}")
    TtoklipResponse<Message> report(@PathVariable Long postId, @RequestBody ReportWebCreate request);

    @Operation(summary = "소통해요 좋아요 추가", description = "소통해요 ID에 해당하는 게시글에 좋아요를 추가합니다.")
    @PostMapping("/like/{postId}")
    TtoklipResponse<Message> registerLike(@PathVariable Long postId);

    @Operation(summary = "소통해요 좋아요 취소", description = "소통해요 ID에 해당하는 게시글에 좋아요를 취소합니다.")
    @DeleteMapping("/like/{postId}")
    TtoklipResponse<Message> cancelLike(@PathVariable Long postId);

    @Operation(summary = "소통해요 스크랩 추가", description = "소통해요 ID에 해당하는 게시글에 스크랩을 추가합니다.")
    @PostMapping("/scrap/{postId}")
    TtoklipResponse<Message> registerScrap(@PathVariable Long postId);

    @Operation(summary = "소통해요 스크랩 취소", description = "소통해요 ID에 해당하는 게시글에 스크랩을 취소합니다.")
    @DeleteMapping("/scrap/{postId}")
    TtoklipResponse<Message> cancelScrap(@PathVariable Long postId);
}
