package com.api.ttoklip.domain.town.community.post.controller;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.question.post.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.town.community.constant.CommunityResponseConstant;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityUpdateRequest;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunitySingleResponse;
import com.api.ttoklip.domain.town.community.post.service.CommunityPostService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Town", description = "우리동네 - 소통해요 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/town/comms")
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    // 소통해요 - community

    /* CREATE */
    @Operation(summary = "소통해요 게시글 생성",
            description = "소통해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.createAndDeleteCommunity,
                                    description = "소통해요 게시글이 생성되었습니다."
                            )))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Long> register(final @Validated @ModelAttribute QuestionCreateRequest request) {
        Long communityId = communityPostService.register(request);
        return new SuccessResponse<>(communityId);
    }

    /* READ */
    @Operation(summary = "소통해요 게시글 조회",
            description = "소통해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.readSingleCommunity,
                                    description = "소통해요 게시글이 조회되었습니다."
                            )))})
    @GetMapping("/{postId}")
    public SuccessResponse<CommunitySingleResponse> getSinglePost(final @PathVariable Long postId) {
        CommunitySingleResponse response = communityPostService.getSinglePost(postId);
        return new SuccessResponse<>(response);
    }

    /* UPDATE */
    @Operation(summary = "소통해요 게시글 수정",
            description = "소통해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.updateCommunity,
                                    description = "소통해요 게시글이 수정되었습니다."
                            )))})
    @PatchMapping("/{commId}")
    public SuccessResponse<Long> updateCommPost(final @PathVariable Long commId,
                                                final @ModelAttribute CommunityUpdateRequest request) {
        communityPostService.updateCommunityPost(commId, request);
        return new SuccessResponse<>(commId);
    }

    /* REPORT */
    @Operation(summary = "소통해요 게시글 신고", description = "소통해요 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @PostMapping("/report/{postId}")
    public SuccessResponse<Long> report(final @PathVariable Long postId,
                                        final @RequestBody ReportCreateRequest request) {
        communityPostService.report(postId, request);
        return new SuccessResponse<>(postId);
    }
}
