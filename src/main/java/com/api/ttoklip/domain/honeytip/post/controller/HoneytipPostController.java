package com.api.ttoklip.domain.honeytip.post.controller;

import com.api.ttoklip.domain.honeytip.post.service.HoneytipPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Honeytip Post", description = "Honeytip Post API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/honeytips/posts")
public class HoneytipPostController {

	private final HoneytipPostService honeytipPostService;

	@Operation(summary = "꿀팁 공유해요 - 개별 게시글 조회", description = "꿀팁 공유해요 개별 게시글을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "개별 게시글 조회 성공"),
		@ApiResponse(responseCode = "400", description = "개별 게시글 조회 실패"),
	})
	@GetMapping("/honeytipSharing/{id}")
	public ResponseEntity<?> seeHoneytipPost() {
		return null;
	}

	@Operation(summary = "꿀팁 공유해요 게시글 생성", description = "꿀팁 공유해요 게시글을 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
		@ApiResponse(responseCode = "400", description = "게시글 생성 실패"),
	})
	@PostMapping("/honeytipSharing")
	public ResponseEntity<?> createHoneytip() {
		return null;
	}

	@Operation(summary = "꿀팁 공유해요 게시글 수정", description = "꿀팁 공유해요 게시글을 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
		@ApiResponse(responseCode = "400", description = "게시글 수정 실패"),
	})
	@PatchMapping("/honeytipSharing/{id}")
	public ResponseEntity<?> updateHoneytip() {
		return null;
	}

	@Operation(summary = "꿀팁 공유해요 게시글 삭제", description = "꿀팁 공유해요 게시글을 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
		@ApiResponse(responseCode = "400", description = "게시글 삭제 실패"),
	})
	@DeleteMapping("/honeytipSharing/{id}")
	public ResponseEntity<?> deleteHoneytip() {
		return null;
	}


}
