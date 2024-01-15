package com.api.ttoklip.domain.honeytip.main.controller;

import com.api.ttoklip.domain.honeytip.main.service.HoneytipMainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Honeytip Main", description = "Honeytip Main API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/honeytips/main")
public class HoneytipMainController {

	private final HoneytipMainService honeytipMainService;

	@Operation(summary = "꿀팁 공유해요 페이지 조회", description = "오늘의 인기 꿀팁 Top5 + 꿀팁 공유해요 데이터를 함께 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "꿀팁 공유해요 홈 조회 성공"),
		@ApiResponse(responseCode = "400", description = "꿀팁 공유해요 홈 조회 실패"),
	})
	@GetMapping
	public ResponseEntity<?> seeHoneytipHome() {
		return null;
	}

	@Operation(summary = "꿀팁 공유해요 하단 탭 조회", description = "꿀팁 공유해요 하단 탭 부분을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "꿀팁 공유해요 탭 조회 성공"),
		@ApiResponse(responseCode = "400", description = "꿀팁 공유해요 탭 조회 실패"),
	})
	@GetMapping("/honeytipSharing")
	public ResponseEntity<?> seeHoneytip() {
		return null;
	}

	@Operation(summary = "꿀팁 공유해요 - 집안일 카테고리 글 조회", description = "꿀팁 공유해요 글 중 집안일 카테고리의 글을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "집안일 카테고리 글 조회 성공"),
		@ApiResponse(responseCode = "400", description = "집안일 카테고리 글 조회 실패"),
	})
	@GetMapping("/honeytipSharing/chores")
	public ResponseEntity<?> seeHoneytipChore() {
		return null;
	}

	@Operation(summary = "꿀팁 공유해요 - 레시피 카테고리 글 조회", description = "꿀팁 공유해요 글 중 레시피 카테고리의 글을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "레시피 카테고리 글 조회 성공"),
		@ApiResponse(responseCode = "400", description = "레시피 카테고리 글 조회 실패"),
	})
	@GetMapping("/honeytipSharing/recipes")
	public ResponseEntity<?> seeHoneytipRecipe() {
		return null;
	}

	@Operation(summary = "꿀팁 공유해요 - 안전한 생활 카테고리 글 조회", description = "꿀팁 공유해요 글 중 안전한 생활 카테고리의 글을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "안전한 생활 카테고리 글 조회 성공"),
		@ApiResponse(responseCode = "400", description = "안전한 생활 카테고리 글 조회 실패"),
	})
	@GetMapping("/honeytipSharing/safelife")
	public ResponseEntity<?> seeHoneytipSafelife() {
		return null;
	}

	@Operation(summary = "꿀팁 공유해요 - 복지/정책 카테고리 글 조회", description = "꿀팁 공유해요 글 중 복지/정책 카테고리의 글을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "복지/정책 카테고리 글 조회 성공"),
		@ApiResponse(responseCode = "400", description = "복지/정책 카테고리 글 조회 실패"),
	})
	@GetMapping("/honeytipSharing/welfaresAndPolicies")
	public ResponseEntity<?> seeHoneytipWelfareAndPolicy() {
		return null;
	}
}
