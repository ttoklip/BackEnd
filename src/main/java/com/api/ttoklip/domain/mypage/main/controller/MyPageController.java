package com.api.ttoklip.domain.mypage.main.controller;

import com.api.ttoklip.domain.mypage.main.constant.MyPageConstant;
import com.api.ttoklip.domain.mypage.main.dto.request.BlockedRequest;
import com.api.ttoklip.domain.mypage.main.dto.request.MyPageRequest;
import com.api.ttoklip.domain.mypage.main.dto.response.*;
import com.api.ttoklip.domain.mypage.main.service.MyPageService;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "My Page", description = "마이페이지 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 정보 불러오기", description = "마이페이지의 기본 정보인 닉네임,동네,레벨,충족도를 가져옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myPageResponse,
                                    description = "마이페이지 정보 조회 성공했습니다"
                            )))})
    @GetMapping()
    public SuccessResponse<MyPageResponse> getMyProfile() {
        return new SuccessResponse<>(myPageService.getMyProfile());
    }

    @Operation(summary = "계정의 사용 제한 정보", description = "계정의 제한 기간과 사유를 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정의 제한 사유 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.restrictedResponse,
                                    description = "제한 기간과 사유를 조회했습니다"
                            )))})
    @GetMapping("/restricted")
    public SuccessResponse<RestricetdResponse> restricted() {
        return new SuccessResponse<>(myPageService.restricted());
    }
    @Operation(summary = "내가 차단한 계정", description = "내가 차단한 계정들을 불러옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "차단계정 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.blockedUsersResponse,
                                    description = "차단한 계정들을 조회했습니다"
                            )))})
    @GetMapping("/blocked")
    public SuccessResponse<BlockedListResponse> blockedUser() {
        return new SuccessResponse<>(myPageService.blockedUser());
    }
    @Operation(summary = "차단을 해제 합니다", description = "차단을 해제 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "차단 해제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.unblockUserResponse,
                                    description = "제한 기간과 사유를 조회했습니다"
                            )))})
    @DeleteMapping("/unblock/{targetId}")
    public SuccessResponse<Message> unblock(@RequestParam String targetId) {
        return new SuccessResponse<>(myPageService.unblock(targetId));//수정 필요
    }
    @Operation(summary = "스크랩한 글 목록", description = "스크랩한 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.scrapedPostsResponse,
                                    description = "스크랩한 글을 불러왔습니다"
                            )))})
    @GetMapping("/scraped-posts")
    public SuccessResponse<ScrapPostsListResponse> scrapPosts() {
        return new SuccessResponse<>(myPageService.scrapPosts());
    }
    @Operation(summary = "내가 작성한 글 목록", description = "내가 작성한 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myPostsResponse,
                                    description = "내가 작성한 글들을 불러왔습니다"
                            )))})
    @GetMapping("/my-posts")
    public SuccessResponse<MyPostsListResponse> myPosts() {
        return new SuccessResponse<>(myPageService.myPosts());
    }
    @Operation(summary = "내가 참여한 거래 목록", description = "내가 참여한 거래 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여한 거래 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.participatedDealsResponse,
                                    description = "참여한 거래를 조회했습니다"
                            )))})
    @GetMapping("/participate-deals")
    public SuccessResponse<ParticipateListResponse> participateDeals() {
        return new SuccessResponse<>(myPageService.participateDeals());
    }

}
