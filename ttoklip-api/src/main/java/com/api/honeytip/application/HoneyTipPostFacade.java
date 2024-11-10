package com.api.honeytip.application;

import com.api.common.upload.Uploader;
import com.api.global.success.Message;
import com.api.honeytip.presentation.request.HoneyTipWebCreate;
import com.api.honeytip.presentation.request.HoneyTipWebEdit;
import com.api.honeytip.presentation.response.HoneyTipSingleResponse;
import com.domain.honeytip.application.HoneyTipCommentService;
import com.domain.honeytip.application.HoneyTipImageService;
import com.domain.honeytip.application.HoneyTipLikeService;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.application.HoneyTipScrapService;
import com.domain.honeytip.application.HoneyTipUrlService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.honeytip.domain.HoneyTipEditor;
import com.domain.honeytip.domain.request.HoneyTipCreate;
import com.domain.honeytip.domain.request.HoneyTipEdit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipPostFacade {

    private final HoneyTipPostService honeyTipPostService;
    private final HoneyTipCommentService honeyTipCommentService;

    private final HoneyTipImageService honeyTipImageService;
    private final HoneyTipScrapService honeyTipScrapService;
    private final HoneyTipLikeService honeyTipLikeService;
    private final HoneyTipUrlService honeyTipUrlService;

    private final ReportService reportService;

    private final MemberService memberService;

    private final Uploader uploader;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    @CheckBadWordCreate
    public Message register(final HoneyTipWebCreate request, final Long currentMemberId) {
        Member currentMember = memberService.findById(currentMemberId);

        HoneyTipCreate honeyTipCreate = HoneyTipCreate.of(request.getTitle(), request.getContent(), request.getCategory());

        HoneyTip honeytip = HoneyTip.of(honeyTipCreate, currentMember);
        honeyTipPostService.saveHoneyTipPost(honeytip);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(honeytip, uploadImages);
        }

        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            registerUrls(honeytip, urls);
        }

        return Message.registerPostSuccess(HoneyTip.class, honeytip.getId());
    }

    private void registerImages(final HoneyTip honeytip, final List<MultipartFile> uploadImages) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> uploadUrls = uploader.uploadMultipartFiles(uploadImages);
        uploadUrls.forEach(uploadUrl -> honeyTipImageService.register(honeytip, uploadUrl));
    }

    private void registerUrls(final HoneyTip honeytip, final List<String> urls) {
        urls.forEach(url -> honeyTipUrlService.register(honeytip, url));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */
    @Transactional
    @CheckBadWordUpdate
    public Message edit(final Long postId, final HoneyTipWebEdit request, final Long currentMemberId) {

        // 기존 게시글 찾기
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(postId);
        honeyTipPostService.checkEditPermission(honeyTip, currentMemberId);

        HoneyTipEdit honeyTipEdit = HoneyTipEdit.of(request.getTitle(), request.getContent());
        // title, content 수정
        HoneyTipEditor postEditor = getPostEditor(honeyTipEdit, honeyTip);
        honeyTip.edit(postEditor);

        // url 수정
        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            // URL 리스트 수정
            honeyTipUrlService.updateHoneyTipUrls(honeyTip, urls);
        }

        // 이미지 추가
        List<MultipartFile> addImages = request.getAddImages();
        if (addImages != null && !addImages.isEmpty()) {
            registerImages(honeyTip, addImages);
        }

        // 이미지 삭제
        List<Long> deleteImageIds = request.getDeleteImageIds();
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            deleteImages(deleteImageIds, currentMemberId);
        }

        return Message.editPostSuccess(HoneyTip.class, honeyTip.getId());
    }

    private HoneyTipEditor getPostEditor(final HoneyTipEdit request, final HoneyTip honeyTip) {
        HoneyTipEditor.HoneyTipEditorBuilder editorBuilder = honeyTip.toEditor();
        return editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    private void deleteImages(final List<Long> deleteImageIds, final Long currentMemberId) {
        honeyTipImageService.deleteImages(deleteImageIds, currentMemberId);
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long postId, final Long currentMemberId) {
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(postId);

        honeyTipPostService.checkEditPermission(honeyTip, currentMemberId);
        honeyTip.deactivate();

        return Message.deletePostSuccess(HoneyTip.class, postId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request, final Long currentMemberId) {
        HoneyTip honeytip = honeyTipPostService.getHoneytip(postId);
        Member currentMember = memberService.findById(currentMemberId);
        reportService.reportHoneyTip(request, honeytip, currentMember);

        return Message.reportPostSuccess(HoneyTip.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- 단건 READ -------------------------------------------- */
    public HoneyTipSingleResponse getSinglePost(final Long postId, final Long currentMemberId) {
        HoneyTip honeyTipWithImgAndUrl = honeyTipPostService.findHoneyTipWithDetails(postId);
        List<HoneyTipComment> activeComments = honeyTipCommentService.findCommentsByHoneyTipId(postId);

        int likeCount = honeyTipLikeService.countHoneyTipLikes(postId).intValue();
        int scrapCount = honeyTipScrapService.countHoneyTipScraps(postId).intValue();

        boolean likedByCurrentUser = honeyTipLikeService.isHoneyTipLikeExists(postId, currentMemberId);
        boolean scrapedByCurrentUser = honeyTipScrapService.isHoneyTipScrapExists(postId, currentMemberId);

        HoneyTipSingleResponse honeyTipSingleResponse = HoneyTipSingleResponse.of(honeyTipWithImgAndUrl,
                activeComments, likeCount, scrapCount, likedByCurrentUser, scrapedByCurrentUser);
        return honeyTipSingleResponse;
    }

    /* -------------------------------------------- 단건 READ 끝 -------------------------------------------- */


    /* -------------------------------------------- 카토고리별 MAIN READ -------------------------------------------- */
    public CategoryResponses getDefaultCategoryRead() {
        List<HoneyTip> houseWorkQuestions = honeyTipPostService.findHouseworkTips();
        List<HoneyTip> recipeQuestions = honeyTipPostService.findRecipeTips();
        List<HoneyTip> safeLivingQuestions = honeyTipPostService.findSafeLivingTips();
        List<HoneyTip> welfarePolicyQuestions = honeyTipPostService.findWelfarePolicyTips();

        return CategoryResponses.builder()
                .housework(convertToTitleResponses(houseWorkQuestions))
                .cooking(convertToTitleResponses(recipeQuestions))
                .safeLiving(convertToTitleResponses(safeLivingQuestions))
                .welfarePolicy(convertToTitleResponses(welfarePolicyQuestions))
                .build();
    }

    private List<TitleResponse> convertToTitleResponses(final List<HoneyTip> honeyTips) {
        return honeyTips.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }

    public List<TitleResponse> getPopularityTop5() {
        List<HoneyTip> top5HoneyTips = honeyTipPostService.getPopularityTop5();
        return top5HoneyTips.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }

    /* -------------------------------------------- 카토고리별 MAIN READ 끝 -------------------------------------------- */


    /* -------------------------------------------- 카테고리별 페이징 -------------------------------------------- */
    public CategoryPagingResponse matchCategoryPaging(final Category category, final Pageable pageable) {
        Page<HoneyTip> questions = honeyTipPostService.matchCategoryPaging(category, pageable);

        List<TitleResponse> data = questions.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();

        return CategoryPagingResponse.builder()
                .data(data)
                .category(category)
                .totalPage(questions.getTotalPages())
                .totalElements(questions.getTotalElements())
                .isLast(questions.isLast())
                .isFirst(questions.isFirst())
                .build();
    }

    /* -------------------------------------------- 카테고리별 페이징 끝 -------------------------------------------- */
}