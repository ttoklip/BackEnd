package com.api.ttoklip.domain.mypage.main.controller;

import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.search.response.NewsletterPaging;
import com.api.ttoklip.domain.mypage.main.constant.MyPageConstant;
import com.api.ttoklip.domain.mypage.main.dto.response.*;
import com.api.ttoklip.domain.mypage.main.service.MyPageService;
import com.api.ttoklip.domain.privacy.dto.PrivacyCreateRequest;
import com.api.ttoklip.domain.privacy.service.ProfileService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "My Page", description = "마이페이지 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/my-page")
public class MyPageController {

    private final static int PAGE_SIZE = 10;

    private final MyPageService myPageService;
    private final ProfileService profileService;

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

    @Operation(summary = "개인정보 수정", description = "프로필 사진, 똑립 전용 닉네임, 자취 경력 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개인정보 수정",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.editMyProfile,
                                    description = "개인정보를 수정했습니다."
                            )))})
    @PatchMapping("/edit")
    public SuccessResponse<Message> edit(@ModelAttribute @Validated final PrivacyCreateRequest request) {
        Message message = profileService.insert(request);
        return new SuccessResponse<>(message);
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
    public SuccessResponse<Message> restricted() {
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
    public SuccessResponse<Message> blockedUser() {
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
    public SuccessResponse<Message> unblock(@PathVariable Long targetId) {
        return new SuccessResponse<>(myPageService.unblock(targetId));//수정 필요
    }
    @Operation(summary = "스크랩한 허니팁 목록", description = "스크랩한 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.scrapHoneyTipsResponse,
                                    description = "스크랩한 허니팁들을 불러왔습니다"
                            )))})
    @GetMapping("/scrap-post/honeytip")
    public SuccessResponse<HoneyTipPaging> scrapHoneyTips(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.scrapHoneyTips(pageable));
    }
    @Operation(summary = "스크랩한 글 목록", description = "스크랩한 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.scrapNewsLetterResponse,
                                    description = "스크랩한 뉴스레터들을 불러왔습니다"
                            )))})
    @GetMapping("/scrap-post/newsletter")
    public SuccessResponse<NewsletterPaging> scrapNewsletters(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.scrapNewsletters(pageable));
    }
    @Operation(summary = "스크랩한 글 목록", description = "스크랩한 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.scrapCommunityResponse,
                                    description = "스크랩한 소통해요들을 불러왔습니다"
                            )))})
    @GetMapping("/scrap-post/community")
    public SuccessResponse<CommunityPaging> scrapCommunity(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.scrapCommunity(pageable));
    }
    @Operation(summary = "내가 작성한 글 목록", description = "내가 작성한 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myHoneyTipsResponse,
                                    description = "내가 작성한 글들을 불러왔습니다"
                            )))})
    @GetMapping("/honeytip")
    public SuccessResponse<HoneyTipPaging> myHoneyTip(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.myHoneyTips(pageable));
    }
    @Operation(summary = "내가 작성한 글 목록", description = "내가 작성한 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myQuestionResponse,
                                    description = "내가 작성한 글들을 불러왔습니다"
                            )))})
    @GetMapping("/question")
    public SuccessResponse<Message> myQuestion(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.myQuestions(pageable));
    }
    @Operation(summary = "내가 작성한 글 목록", description = "내가 작성한 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myCommunityResponse,
                                    description = "내가 작성한 글들을 불러왔습니다"
                            )))})
    @GetMapping("/community")
    public SuccessResponse<CommunityPaging> myCommunity(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.myCommunities(pageable));
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
    public SuccessResponse<Message> participateDeals(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.participateDeals());
    }

}
