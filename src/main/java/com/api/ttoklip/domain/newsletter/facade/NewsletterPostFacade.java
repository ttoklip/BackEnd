package com.api.ttoklip.domain.newsletter.facade;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.vo.Role;
import com.api.ttoklip.domain.newsletter.controller.dto.request.NewsletterCreateRequest;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsCategoryPagingResponse;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterSingleResponse;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterThumbnailResponse;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.service.NewsletterCommentService;
import com.api.ttoklip.domain.newsletter.service.NewsletterImageService;
import com.api.ttoklip.domain.newsletter.service.NewsletterLikeService;
import com.api.ttoklip.domain.newsletter.service.NewsletterPostService;
import com.api.ttoklip.domain.newsletter.service.NewsletterScrapService;
import com.api.ttoklip.domain.newsletter.service.NewsletterUrlService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterPostFacade {

    private final NewsletterPostService postService;
    private final NewsletterCommentService commentService;
    private final NewsletterImageService imageService;
    private final NewsletterUrlService urlService;

    private final NewsletterScrapService scrapService;
    private final NewsletterLikeService likeService;

    private final ReportService reportService;
    private final S3FileUploader s3FileUploader;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final NewsletterCreateRequest request) {
        Member currentMember = getCurrentMember();
        String mainImageUrl = registerMainImage(request);

        Newsletter newsletter = Newsletter.of(request, mainImageUrl, currentMember);
        postService.saveNewsletter(newsletter);

        List<MultipartFile> images = request.getSubImages();
        if (images != null && !images.isEmpty()) {
            registerImages(newsletter, images);
        }

        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            registerUrls(newsletter, urls);
        }

        return Message.registerPostSuccess(Newsletter.class, newsletter.getId());
    }

    private String registerMainImage(final NewsletterCreateRequest request) {
        MultipartFile mainImage = request.getMainImage();
        return uploadImage(mainImage);
    }

    private String uploadImage(final MultipartFile uploadImage) {
        return s3FileUploader.uploadMultipartFile(uploadImage);
    }

    private void registerImages(final Newsletter newsletter, final List<MultipartFile> uploadImages) {
        List<String> uploadUrls = uploadImages(uploadImages);
        uploadUrls.forEach(uploadUrl -> imageService.register(newsletter, uploadUrl));
    }

    private List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return s3FileUploader.uploadMultipartFiles(uploadImages);
    }

    private void registerUrls(final Newsletter newsletter, final List<String> urls) {
        urls.forEach(url -> urlService.register(newsletter, url));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */

    @Transactional
    public Message delete(final Long postId) {
        Newsletter newsletter = postService.getNewsletter(postId);

        checkManagerPermission();
        newsletter.deactivate();

        return Message.deletePostSuccess(Newsletter.class, postId);
    }

    private void checkManagerPermission() {
        Member currentMember = getCurrentMember();

        if (!currentMember.getRole().equals(Role.MANAGER)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_DELETE_POST);
        }
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- 단건 READ -------------------------------------------- */

    public NewsletterSingleResponse getSinglePost(final Long postId) {

        Newsletter newsletterWithImg = postService.findNewsletterWithDetails(postId);
        List<NewsletterComment> activeComments = commentService.findCommentsByNewsletterId(postId);
        int likeCount = likeService.countNewsletterLikes(postId).intValue();
        int scrapCount = scrapService.countNewsletterScraps(postId).intValue();

        boolean likedByCurrentUser = likeService.isNewsletterExists(postId);
        boolean scrapedByCurrentUser = scrapService.isNewsletterExists(postId);

        NewsletterSingleResponse newsletterSingleResponse = NewsletterSingleResponse.toDto(newsletterWithImg,
                activeComments, likeCount, scrapCount, likedByCurrentUser, scrapedByCurrentUser);
        return newsletterSingleResponse;
    }

    /* -------------------------------------------- 단건 READ 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        Newsletter newsletter = postService.getNewsletter(postId);
        reportService.reportNewsletter(request, newsletter);

        return Message.reportPostSuccess(Newsletter.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- 카테고리별 페이징  -------------------------------------------- */

    public NewsCategoryPagingResponse getPagingCategory(final String inputCategory, final Pageable pageable) {
        Category category = Category.valueOf(inputCategory);
        Page<Newsletter> contentPaging = postService.getPaging(category, pageable);

        // List<Entity>
        List<Newsletter> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<NewsletterThumbnailResponse> newsletterThumbnailResponses = convertToCategoryResponse(contents);

        return NewsCategoryPagingResponse.builder()
                .newsletterThumbnailRespons(newsletterThumbnailResponses)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public List<NewsletterThumbnailResponse> convertToCategoryResponse(final List<Newsletter> newsletters) {
        return newsletters.stream()
                .map(NewsletterThumbnailResponse::from)
                .toList();
    }

    /* -------------------------------------------- 카테고리별 페이징 끝 -------------------------------------------- */

    /*
    아래 기능 미사용
    public CategoryResponses getDefaultCategoryRead() {
        List<Question> houseWorkQuestions = questionDefaultRepository.getHouseWork();
        List<Question> recipeQuestions = questionDefaultRepository.getRecipe();
        List<Question> safeLivingQuestions = questionDefaultRepository.getSafeLiving();
        List<Question> welfarePolicyQuestions = questionDefaultRepository.getWelfarePolicy();

        return CategoryResponses.builder()
                .housework(convertToTitleResponses(houseWorkQuestions))
                .cooking(convertToTitleResponses(recipeQuestions))
                .safeLiving(convertToTitleResponses(safeLivingQuestions))
                .welfarePolicy(convertToTitleResponses(welfarePolicyQuestions))
                .build();
    }

    private List<TitleResponse> convertToTitleResponses(final List<Question> questions) {
        return questions.stream()
                .map(TitleResponse::questionOf)
                .toList();
    }

     */
}
