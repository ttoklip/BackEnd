package com.api.ttoklip.domain.town.community.controller;

import com.api.ttoklip.domain.town.community.dto.request.CommCreateRequest;
import com.api.ttoklip.domain.town.community.dto.request.CommSearchCondition;
import com.api.ttoklip.domain.town.community.dto.request.CommUpdateRequest;
import com.api.ttoklip.domain.town.community.dto.response.CommListResponse;
import com.api.ttoklip.domain.town.community.dto.response.CommResponse;
import com.api.ttoklip.domain.town.community.service.CommService;
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

import java.awt.print.Pageable;

@Tag(name = "Town", description = "우리동네 - 소통해요 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/town")
public class CommController {

    private final CommService commService;

    // 소통해요 - comm
    @Operation(summary = "소통해요 더보기 페이지 조회",
            description = "소통해요 더보기 페이지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 더보기 페이지 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommListResponse.class))),
            @ApiResponse(responseCode = "400", description = "소통해요 더보기 페이지 조회 실패"),
    })
    @GetMapping("/comms")
    public SuccessResponse<CommListResponse> getCommPage(final @Validated @ModelAttribute CommSearchCondition condition,
                                                         final Pageable pageable) {
        CommListResponse commListResponse = commService.searchCommPaging(condition, pageable);
        return new SuccessResponse<>(commListResponse);
    }

    @Operation(summary = "소통해요 게시글 조회",
            description = "소통해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommResponse.class))),
            @ApiResponse(responseCode = "400", description = "소통해요 게시글 조회 실패"),
    })
    @GetMapping("/comms/{commId}")
    public SuccessResponse<CommResponse> getComm(final @PathVariable Long commId) {
        CommResponse commResponse = commService.getComm(commId);
        return new SuccessResponse<>(commResponse);
    }

    @Operation(summary = "소통해요 게시글 생성",
            description = "소통해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CommResponse.class))),
            @ApiResponse(responseCode = "400", description = "소통해요 게시글 생성 실패"),
    })
    @PostMapping("/comms")
    public SuccessResponse<CommResponse> createCommPost(final @Validated @ModelAttribute CommCreateRequest request) {
        CommResponse commResponse = commService.createCommPost(request);
        return new SuccessResponse<>(commResponse);
    }

    @Operation(summary = "소통해요 게시글 수정",
            description = "소통해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "소통해요 게시글 수정 실패"),
    })
    @PatchMapping("/comms/{commId}")
    public SuccessResponse<Long> updateCommPost(final @PathVariable Long commId,
                                                final @ModelAttribute CommUpdateRequest request) {
        commService.updateCommPost(commId, request);
        return new SuccessResponse<>(commId);
    }

    @Operation(summary = "소통해요 게시글 삭제",
            description = "소통해요 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 삭제 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "소통해요 게시글 삭제 실패"),
    })
    @DeleteMapping("/comms/{commId}")
    public SuccessResponse<Long> deleteCommPost(final @PathVariable Long commId) {
        commService.deleteCommPost(commId);
        return new SuccessResponse<>(commId);
    }
}
