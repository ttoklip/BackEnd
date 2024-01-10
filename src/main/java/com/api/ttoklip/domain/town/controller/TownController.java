package com.api.ttoklip.domain.town.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Town", description = "Town API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/town")
public class TownController {

    @Operation(summary = "함께해요 더보기 페이지 조회",
            description = "함께해요 더보기 페이지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 더보기 페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "함께해요 더보기 페이지 조회 실패"),
    })
    @GetMapping("/carts")
    public ResponseEntity<?> getCartPage() {
        return null;
    }

    @Operation(summary = "함께해요 게시글 조회",
            description = "함께해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "함께해요 게시글 조회 실패"),
    })
    @GetMapping("/carts/{cartId}")
    public ResponseEntity<?> getCartPost() {
        return null;
    }

    @Operation(summary = "함께해요 게시글 생성",
            description = "함께해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "함께해요 게시글 생성 실패"),
    })
    @PostMapping("/carts/{cartId}")
    public ResponseEntity<?> createCartPost() { return null; }

    // 함께해요는 글 삭제 불가능, 수정만 ok
    @Operation(summary = "함께해요 게시글 수정",
            description = "함께해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "함께해요 게시글 수정 실패"),
    })
    @PatchMapping("/carts/{cartId}")
    public ResponseEntity<?> updateCartPost() {
        return null;
    }

    @Operation(summary = "소통해요 더보기 페이지 조회",
            description = "소통해요 더보기 페이지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 더보기 페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "소통해요 더보기 페이지 조회 실패"),
    })
    @GetMapping("/comms")
    public ResponseEntity<?> getCommsPage() {
        return null;
    }

    @Operation(summary = "소통해요 게시글 조회",
            description = "소통해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "소통해요 게시글 조회 실패"),
    })
    @GetMapping("/comms/{commsId}")
    public ResponseEntity<?> getCommsPost() {
        return null;
    }

    @Operation(summary = "소통해요 게시글 생성",
            description = "소통해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "소통해요 게시글 생성 실패"),
    })
    @PostMapping("/comms/{commsId}")
    public ResponseEntity<?> createCommsPost() { return null; }

    @Operation(summary = "소통해요 게시글 수정",
            description = "소통해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "소통해요 게시글 수정 실패"),
    })
    @PatchMapping("/comms/{commsId}")
    public ResponseEntity<?> updateCommsPost() {
        return null;
    }

    @Operation(summary = "소통해요 게시글 삭제",
            description = "소통해요 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "소통해요 게시글 삭제 실패"),
    })
    @DeleteMapping("/comms/{commsId}")
    public ResponseEntity<?> deleteCommsPost() {
        return null;
    }

}
