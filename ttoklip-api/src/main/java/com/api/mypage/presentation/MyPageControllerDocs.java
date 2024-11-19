package com.api.mypage.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
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
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "My Page", description = "마이페이지 API")
@RequestMapping("/api/v1/my-page")
public interface MyPageControllerDocs {

    @Operation(summary = "마이페이지 정보 불러오기")
    @GetMapping
    TtoklipResponse<TargetMemberProfile> getMyProfile();

    @Operation(summary = "개인정보 수정", description = "프로필 사진, 똑립 전용 닉네임, 자취 경력 수정")
    @PatchMapping("/edit")
    TtoklipResponse<Message> edit(@ModelAttribute @Validated ProfileWebCreate request);

    @Operation(summary = "스크랩한 허니팁 목록", description = "스크랩한 허니팁 목록 불러오기")
    @GetMapping("/scrap-post/honeytip")
    TtoklipResponse<HoneyTipPaging> scrapHoneyTips(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "스크랩한 뉴스레터 목록", description = "스크랩한 뉴스레터 목록 불러오기")
    @GetMapping("/scrap-post/newsletter")
    TtoklipResponse<NewsletterPaging> scrapNewsletters(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "스크랩한 소통해요 목록", description = "스크랩한 소통해요 목록 불러오기")
    @GetMapping("/scrap-post/community")
    TtoklipResponse<CommunityPaging> scrapCommunity(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "내가 작성한 꿀팁 목록", description = "내가 작성한 꿀팁 목록 불러오기")
    @GetMapping("/honeytip")
    TtoklipResponse<HoneyTipPaging> myHoneyTip(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "내가 작성한 질문해요 목록", description = "내가 작성한 질문해요 목록 불러오기")
    @GetMapping("/question")
    TtoklipResponse<QuestionPaging> myQuestion(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "내가 작성한 소통해요 목록", description = "내가 작성한 소통해요 목록 불러오기")
    @GetMapping("/community")
    TtoklipResponse<CommunityPaging> myCommunity(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "내가 참여한 거래 목록", description = "내가 참여한 거래 목록 불러오기")
    @GetMapping("/participate-deals")
    TtoklipResponse<CartPaging> participateDeals(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);
}
