package com.api.ttoklip.domain.mypage.controller;

import com.api.ttoklip.domain.profile.controller.response.TargetMemberProfile;
import com.api.ttoklip.domain.mypage.constant.MyPageConstant;
import com.api.ttoklip.domain.mypage.dto.response.QuestionPaging;
import com.api.ttoklip.domain.mypage.service.MyPageService;
import com.api.ttoklip.domain.privacy.dto.PrivacyCreateRequest;
import com.api.ttoklip.domain.profile.service.ProfileService;
import com.api.ttoklip.domain.search.response.CartPaging;
import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.search.response.NewsletterPaging;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "My Page", description = "마이페이지 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/my-page")
public class MyPageController {

    private final static int PAGE_SIZE = 10;

    private final MyPageService myPageService;
    private final ProfileService profileService;

    @Operation(summary = "마이페이지 정보 불러오기")
    @GetMapping
    public SuccessResponse<TargetMemberProfile> getMyProfile() {
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
        Message message = profileService.edit(request);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "스크랩한 허니팁 목록", description = "스크랩한 허니팁 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 허니팁 성공",
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

    @Operation(summary = "스크랩한 뉴스레터 목록", description = "스크랩한 뉴스레터 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 뉴스레터 성공",
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

    @Operation(summary = "스크랩한 소통해요 목록", description = "스크랩한 소통해요 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 소통해요 성공",
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

    @Operation(summary = "내가 작성한 꿀팁 목록", description = "내가 작성한 꿀팁 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 꿀팁 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myHoneyTipsResponse,
                                    description = "내가 작성한 꿀팁 들을 불러왔습니다"
                            )))})
    @GetMapping("/honeytip")
    public SuccessResponse<HoneyTipPaging> myHoneyTip(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.myHoneyTips(pageable));
    }

    @Operation(summary = "내가 작성한 질문해요 목록", description = "내가 작성한 질문해요 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myQuestionResponse,
                                    description = "내가 작성한 질문해요들을 불러왔습니다"
                            )))})
    @GetMapping("/question")
    public SuccessResponse<QuestionPaging> myQuestion(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.myQuestions(pageable));
    }

    @Operation(summary = "내가 작성한 소통해요 목록", description = "내가 작성한 소통해요 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myCommunityResponse,
                                    description = "내가 작성한 소통해요들을 불러왔습니다"
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
    public SuccessResponse<CartPaging> participateDeals(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(myPageService.participateDeals(pageable));
    }

}
