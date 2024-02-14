package com.api.ttoklip.domain.newsletter.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.image.service.NewsletterImageService;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.dto.request.NewsletterCreateReq;
import com.api.ttoklip.domain.newsletter.post.dto.response.NewsletterWithCommentRes;
import com.api.ttoklip.domain.newsletter.post.repository.NewsletterRepository;
import com.api.ttoklip.domain.newsletter.url.service.NewsletterUrlService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterPostService {

    private final NewsletterRepository newsletterRepository;
    private final S3FileUploader s3FileUploader;
    private final NewsletterImageService imageService;
    private final NewsletterUrlService urlService;
    private final ReportService reportService;


    /* -------------------------------------------- 존재 여부 확인 -------------------------------------------- */
    public Newsletter findById(final Long postId) {
        return newsletterRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.NEWSLETTER_NOT_FOUND));
    }
    /* -------------------------------------------- 존재 여부 확인 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final NewsletterCreateReq request) {

        String mainImageUrl = registerMainImage(request);

        Newsletter newsletter = Newsletter.from(request, mainImageUrl);
        newsletterRepository.save(newsletter);

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

    private String registerMainImage(final NewsletterCreateReq request) {
        MultipartFile mainImage = request.getMainImage();
        return uploadImage(mainImage);
    }

    private String uploadImage(final MultipartFile uploadImage) {
        return s3FileUploader.uploadMultipartFile(uploadImage);
    }

    private void registerImages(final Newsletter newsletter, final List<MultipartFile> uploadImages) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
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


    /* -------------------------------------------- FETCH JOIN READ -------------------------------------------- */
    public NewsletterWithCommentRes getSinglePost(final Long postId) {
        Newsletter newsletter = newsletterRepository.findByIdFetchJoin(postId);
        List<NewsletterComment> activeComments = newsletterRepository.findActiveCommentsByNewsletterId(postId);
        NewsletterWithCommentRes newsletterWithCommentRes = NewsletterWithCommentRes.toDto(newsletter, activeComments);
        return newsletterWithCommentRes;
    }
    /* -------------------------------------------- 단건 READ 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        Newsletter newsletter = findById(postId);
        reportService.reportNewsletter(request, newsletter);
        return Message.reportPostSuccess(Newsletter.class, postId);
    }
    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- total entity count -------------------------------------------- */

    public Long getEntityCount() {
        return newsletterRepository.findNewsletterCount();
    }

    /* -------------------------------------------- total entity count 끝 -------------------------------------------- */

    public List<Newsletter> getContentWithPageable(final Pageable pageable) {
        return newsletterRepository.findAll(pageable).getContent();
    }


    /* -------------------------------------------- SCRAP -------------------------------------------- */
    public Message registerScrap(Long postId) {
        return null;
    }

    public static Message cancelScrap(Long postId) {
        return null;

    }
    /* -------------------------------------------- SCRAP 끝 -------------------------------------------- */


//    public CategoryResponses getDefaultCategoryRead() {
//        List<Question> houseWorkQuestions = questionDefaultRepository.getHouseWork();
//        List<Question> recipeQuestions = questionDefaultRepository.getRecipe();
//        List<Question> safeLivingQuestions = questionDefaultRepository.getSafeLiving();
//        List<Question> welfarePolicyQuestions = questionDefaultRepository.getWelfarePolicy();
//
//        return CategoryResponses.builder()
//                .housework(convertToTitleResponses(houseWorkQuestions))
//                .cooking(convertToTitleResponses(recipeQuestions))
//                .safeLiving(convertToTitleResponses(safeLivingQuestions))
//                .welfarePolicy(convertToTitleResponses(welfarePolicyQuestions))
//                .build();
//    }
//
//    private List<TitleResponse> convertToTitleResponses(final List<Question> questions) {
//        return questions.stream()
//                .map(TitleResponse::questionOf)
//                .toList();
//    }
}
