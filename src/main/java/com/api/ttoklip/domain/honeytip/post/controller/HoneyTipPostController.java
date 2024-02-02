package com.api.ttoklip.domain.honeytip.post.controller;

import com.api.ttoklip.domain.honeytip.post.dto.request.HoneyTipCreateReq;
import com.api.ttoklip.domain.honeytip.post.constant.HoneyTipResponseConstant;
import com.api.ttoklip.domain.honeytip.post.dto.request.HoneyTipEditReq;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipPostService;
import com.api.ttoklip.global.success.Message;
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

@Tag(name = "HoneyTip Post", description = "HoneyTip Post API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/honeytips/posts")
public class HoneyTipPostController {

	private final HoneyTipPostService honeytipPostService;

	/* CREATE */
	@Operation(summary = "새로운 꿀팁 생성", description = "form/data로 새로운 꿀팁 게시글을 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "꿀팁 게시글 생성 성공",
				content = @Content(
						mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
						schema = @Schema(implementation = SuccessResponse.class),
						examples = @ExampleObject(
								name = "SuccessResponse",
								value = HoneyTipResponseConstant.createAndDeleteHoneytip,
								description = "질문이 생성되었습니다."
	)))})
	@PostMapping
	public SuccessResponse<Long> register(final @Validated @ModelAttribute HoneyTipCreateReq request) {
		return new SuccessResponse<>(honeytipPostService.register(request));
	}


	/* UPDATE */
	@Operation(summary = "꿀팁 게시글 수정", description = "꿀팁 공유해요 게시글을 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "꿀팁 게시글 수정 성공",
				content = @Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = SuccessResponse.class),
						examples = @ExampleObject(
								name = "SuccessResponse",
								value = HoneyTipResponseConstant.createAndDeleteHoneytip,
								description = "꿀팁이 수정되었습니다."
						)))})
	@PatchMapping("/{postId}")
	public SuccessResponse<Message> edit(final @PathVariable Long postId, final @RequestBody HoneyTipEditReq request) {
		return new SuccessResponse<>(honeytipPostService.edit(postId, request));
	}


	/* DELETE */
	@Operation(summary = "꿀팁 게시글 삭제", description = "꿀팁 ID에 해당하는 게시글을 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "꿀팁 게시글 삭제 성공",
				content = @Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = SuccessResponse.class),
						examples = @ExampleObject(
								name = "SuccessResponse",
								value = HoneyTipResponseConstant.createAndDeleteHoneytip,
								description = "꿀팁이 삭제되었습니다."
	)))})
	@DeleteMapping("/{postId}")
	public SuccessResponse<Message> delete(final @PathVariable Long postId) {
		return new SuccessResponse<>(honeytipPostService.delete(postId));
	}


}
