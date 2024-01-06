package com.api.ttoklip.domain.honeytip.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Honeytips", description = "Honeytips API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/honeytips")
public class HoneytipController {

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

    @Operation(summary = "질문해요 하단 탭 조회", description = "질문해요 하단 탭 부분을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문해요 탭 조회 성공"),
            @ApiResponse(responseCode = "400", description = "질문해요 탭 조회 실패"),
    })
    @GetMapping("/ask-questions")
    public ResponseEntity<?> seeAskQuestions() {
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

    @Operation(summary = "질문해요 - 개별 게시글 조회", description = "질문해요의 개별 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개별 게시글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "개별 게시글 조회 실패"),
    })
    @GetMapping("/ask-questions/{id}")
    public ResponseEntity<?> seeAskQuestion() {
        return null;
    }

    @Operation(summary = "질문해요 게시글 생성", description = "질문해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 생성 실패"),
    })
    @PostMapping("/ask-questions")
    public ResponseEntity<?> createAskQuestion() {
        return null;
    }

    @Operation(summary = "질문해요 게시글 수정", description = "질문해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 수정 실패"),
    })
    @PatchMapping("/ask-questions/{id}")
    public ResponseEntity<?> updateAskQuestion() {
        return null;
    }

    @Operation(summary = "질문해요 게시글 삭제", description = "질문해요 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 삭제 실패"),
    })
    @DeleteMapping("/ask-questions/{id}")
    public ResponseEntity<?> deleteAskQuestion() {
        return null;
    }

    @Operation(summary = "질문해요 - 집안일 카테고리 글 조회", description = "질문해요 글 중 집안일 카테고리의 글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "집안일 카테고리 글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "집안일 카테고리 글 조회 실패"),
    })
    @GetMapping("/ask-questions/chores")
    public ResponseEntity<?> seeAskQuestionChore() {
        return null;
    }

    @Operation(summary = "질문해요 - 요리 카테고리 글 조회", description = "질문해요 글 중 요리 카테고리의 글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요리 카테고리 글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "요리 카테고리 글 조회 실패"),
    })
    @GetMapping("/ask-questions/cooking")
    public ResponseEntity<?> seeAskQuestionRecipe() {
        return null;
    }

    @Operation(summary = "질문해요 - 안전한 생활 카테고리 글 조회", description = "질문해요 글 중 안전한 생활 카테고리의 글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "안전한 생활 카테고리 글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "안전한 생활 카테고리 글 조회 실패"),
    })
    @GetMapping("/ask-questions/safelife")
    public ResponseEntity<?> seeAskQuestionSafelife() {
        return null;
    }

    // 질문해요의 복지/정책 클릭 시, 질문해요 글 중 복지/정책 카테고리로 나뉘어진 글만 표시
    @Operation(summary = "질문해요 - 복지/정책 카테고리 글 조회", description = "질문해요 글 중 복지/정책 카테고리의 글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "복지/정책 카테고리 글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "복지/정책 카테고리 글 조회 실패"),
    })
    @GetMapping("/ask-questions/welfaresAndPolicies")
    public ResponseEntity<?> seeAskQuestionWelfareAndPolicy() {
        return null;
    }




}
