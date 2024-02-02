package com.api.ttoklip.domain.mypage.controller;

import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneytipCreateReq;
import com.api.ttoklip.domain.mypage.constant.MyPageConstant;
import com.api.ttoklip.domain.mypage.dto.request.MyPageRequest;
import com.api.ttoklip.domain.mypage.dto.response.MyPageResponse;
import com.api.ttoklip.domain.mypage.dto.response.NoticeListResponse;
import com.api.ttoklip.domain.mypage.dto.response.TermsResponse;
import com.api.ttoklip.domain.mypage.service.MyPageService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
    @GetMapping
    public SuccessResponse<MyPageResponse> getMyProfile(final MyPageRequest myPageRequest) {
        return new SuccessResponse<>(myPageService.getMyProfile(myPageRequest));
    }
    @Operation(summary = "공지사항 불러오기", description = "공지사항 목록을 가져옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.noticeResponse,
                                    description = "공지사항이 조회되었습니다"
                            )))})
    @GetMapping("/notice")
    public SuccessResponse<NoticeListResponse> noticeList() {
        return new SuccessResponse<>(myPageService.noticeList());
    }
    @Operation(summary = "이용약관 불러오기", description = "이용약관을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.termsAndPolicyResponse,
                                    description = "이용약관을 조회했습니다"
                            )))})
    @GetMapping("/term")
    public SuccessResponse<TermsResponse> term(final String termType) {
        return new SuccessResponse<>(myPageService.term(termType));
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
    @GetMapping("/restricted/{accountId}")
    public SuccessResponse<Long> register(final @Validated @ModelAttribute HoneytipCreateReq request) {
        return new SuccessResponse<>(myPageService.register(request));
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
    @GetMapping("/blocked/{accountId}")
    public SuccessResponse<Long> register(final @Validated @ModelAttribute HoneytipCreateReq request) {
        return new SuccessResponse<>(myPageService.register(request));
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
    @DeleteMapping("/unblock/{blocked-accountId}")
    public SuccessResponse<Long> register(final @Validated @ModelAttribute HoneytipCreateReq request) {
        return new SuccessResponse<>(myPageService.register(request));
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
    public SuccessResponse<Long> register(final @Validated @ModelAttribute HoneytipCreateReq request) {
        return new SuccessResponse<>(myPageService.register(request));
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
    public SuccessResponse<Long> register(final @Validated @ModelAttribute HoneytipCreateReq request) {
        return new SuccessResponse<>(myPageService.register(request));
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
    @GetMapping("/participat-deals")
    public SuccessResponse<Long> register(final @Validated @ModelAttribute HoneytipCreateReq request) {
        return new SuccessResponse<>(myPageService.register(request));
    }
}
