package com.api.ttoklip.domain.town.cart.post.controller;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartSingleResponse;
import com.api.ttoklip.domain.town.cart.post.service.CartPostService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Town", description = "우리동네 - 함께해요 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/town/carts")
public class CartPostController {

    private final CartPostService cartPostService;

    // 함께해요 - cart

    /* CREATE */
    @Operation(summary = "함께해요 게시글 생성",
            description = "함께해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Message> register(final @Validated @ModelAttribute CartCreateRequest request) {
        Message message = cartPostService.register(request);
        return new SuccessResponse<>(message);
    }

    /* READ */
    @Operation(summary = "함께해요 게시글 조회",
            description = "함께해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 조회 성공",
                    content = @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CartSingleResponse.class)
                    ))})
    @GetMapping("/{postId}")
    public SuccessResponse<CartSingleResponse> getSinglePost(final @PathVariable Long postId) {
        CartSingleResponse response = cartPostService.getSinglePost(postId);
        return new SuccessResponse<>(response);
    }

    /* UPDATE */
    @Operation(summary = "함께해요 게시글 수정",
            description = "함께해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)
                    ))})
    @PatchMapping("/{postId}")
    public SuccessResponse<Message> edit(final @PathVariable Long postId,
                                         final @Validated @ModelAttribute CartCreateRequest request) {
        return new SuccessResponse<>(cartPostService.edit(postId, request));
    }

    /* REPORT */
    @Operation(summary = "함께해요 게시글 신고", description = "함께해요 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @PostMapping("/report/{postId}")
    public SuccessResponse<Message> report(final @PathVariable Long postId,
                                        final @RequestBody ReportCreateRequest request) {
        Message message = cartPostService.report(postId, request);
        return new SuccessResponse<>(message);
    }
}
