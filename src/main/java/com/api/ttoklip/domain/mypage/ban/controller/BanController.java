package com.api.ttoklip.domain.mypage.ban.controller;

import com.api.ttoklip.domain.mypage.ban.constant.BanConstant;
import com.api.ttoklip.domain.mypage.ban.service.BanService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ban", description = "차단 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ban")
public class BanController {

    private final BanService banService;

    //Todo 여기에 차단과 차단해제 기능 추가 부탁합니다 api는 만들어 뒀어요

    @Operation(summary = "내가 차단한 계정", description = "내가 차단한 계정들을 불러옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "차단계정 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = BanConstant.banUsersResponse,
                                    description = "차단한 계정들을 조회했습니다"
                            )))})
    @GetMapping()
    public SuccessResponse<Message> blockedUser() {
        return new SuccessResponse<>(banService.banUsersList());//Todo 함수 로직 수정 필요
    }

    @Operation(summary = "계정 차단", description = "계정을 차단합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정 차단 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = BanConstant.userBanResponse,
                                    description = "계정을 차단하였습니다"
                            )))})
    @PostMapping("/{targetId}")
    public SuccessResponse<Message> blockedUser(@PathVariable Long targetId) {
        return new SuccessResponse<>(banService.userBan());//Todo 함수 로직 수정 필요
    }

    @Operation(summary = "차단을 해제 합니다", description = "차단을 해제 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "차단 해제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = BanConstant.deleteBanUserResponse,
                                    description = "제한 기간과 사유를 조회했습니다"
                            )))})
    @DeleteMapping("/delete/{targetId}")
    public SuccessResponse<Message> unblock(@PathVariable Long targetId) {
        return new SuccessResponse<>(banService.deleteBanUser());//Todo 함수 로직 수정 필요
    }
}
