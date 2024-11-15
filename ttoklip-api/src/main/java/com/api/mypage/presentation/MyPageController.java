package com.api.mypage.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.mypage.application.MyPageFacade;
import com.api.profile.application.ProfileFacade;
import com.api.profile.presentation.ProfileWebCreate;
import com.api.question.presentation.dto.response.QuestionPaging;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.search.presentation.response.NewsletterPaging;
import com.domain.profile.application.response.TargetMemberProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

    private final MyPageFacade myPageFacade;
    private final ProfileFacade profileFacade;

    @Operation(summary = "마이페이지 정보 불러오기")
    @GetMapping
    public TtoklipResponse<TargetMemberProfile> getMyProfile() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.getMyProfile(currentMemberId));
    }

    @Operation(summary = "개인정보 수정", description = "프로필 사진, 똑립 전용 닉네임, 자취 경력 수정")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "개인정보 수정",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.editMyProfile,
                                    description = "개인정보를 수정했습니다."
                            )))})
    @PatchMapping("/edit")
    public TtoklipResponse<Message> edit(@ModelAttribute @Validated final ProfileWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileFacade.edit(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Operation(summary = "스크랩한 허니팁 목록", description = "스크랩한 허니팁 목록 불러오기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "스크랩 허니팁 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.scrapHoneyTipsResponse,
                                    description = "스크랩한 허니팁들을 불러왔습니다"
                            )))})
    @GetMapping("/scrap-post/honeytip")
    public TtoklipResponse<HoneyTipPaging> scrapHoneyTips(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapHoneyTips(currentMemberId, pageable));
    }

    @Operation(summary = "스크랩한 뉴스레터 목록", description = "스크랩한 뉴스레터 목록 불러오기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "스크랩 뉴스레터 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.scrapNewsLetterResponse,
                                    description = "스크랩한 뉴스레터들을 불러왔습니다"
                            )))})
    @GetMapping("/scrap-post/newsletter")
    public TtoklipResponse<NewsletterPaging> scrapNewsletters(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapNewsletters(currentMemberId, pageable));
    }

    @Operation(summary = "스크랩한 소통해요 목록", description = "스크랩한 소통해요 목록 불러오기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "스크랩 소통해요 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.scrapCommunityResponse,
                                    description = "스크랩한 소통해요들을 불러왔습니다"
                            )))})
    @GetMapping("/scrap-post/community")
    public TtoklipResponse<CommunityPaging> scrapCommunity(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.scrapCommunity(currentMemberId, pageable));
    }

    @Operation(summary = "내가 작성한 꿀팁 목록", description = "내가 작성한 꿀팁 목록 불러오기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "작성한 꿀팁 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myHoneyTipsResponse,
                                    description = "내가 작성한 꿀팁 들을 불러왔습니다"
                            )))})
    @GetMapping("/honeytip")
    public TtoklipResponse<HoneyTipPaging> myHoneyTip(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myHoneyTips(currentMemberId, pageable));
    }

    @Operation(summary = "내가 작성한 질문해요 목록", description = "내가 작성한 질문해요 목록 불러오기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myQuestionResponse,
                                    description = "내가 작성한 질문해요들을 불러왔습니다"
                            )))})
    @GetMapping("/question")
    public TtoklipResponse<QuestionPaging> myQuestion(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myQuestions(currentMemberId, pageable));
    }

    @Operation(summary = "내가 작성한 소통해요 목록", description = "내가 작성한 소통해요 목록 불러오기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.myCommunityResponse,
                                    description = "내가 작성한 소통해요들을 불러왔습니다"
                            )))})
    @GetMapping("/community")
    public TtoklipResponse<CommunityPaging> myCommunity(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.myCommunities(currentMemberId, pageable));
    }

    @Operation(summary = "내가 참여한 거래 목록", description = "내가 참여한 거래 목록 불러오기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "참여한 거래 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = MyPageConstant.participatedDealsResponse,
                                    description = "참여한 거래를 조회했습니다"
                            )))})
    @GetMapping("/participate-deals")
    public TtoklipResponse<CartPaging> participateDeals(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(myPageFacade.participateDeals(currentMemberId, pageable));
    }

}
