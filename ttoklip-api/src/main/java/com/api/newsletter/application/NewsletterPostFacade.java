package com.api.newsletter.application;

import com.api.common.ReportWebCreate;
import com.api.common.upload.Uploader;
import com.api.global.success.Message;
import com.api.newsletter.presentation.NewsletterWebCreate;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.report.domain.ReportCreate;
import com.domain.common.report.application.ReportService;
import com.domain.common.vo.Category;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.member.domain.vo.Role;
import com.domain.newsletter.application.NewsletterCommentService;
import com.domain.newsletter.application.NewsletterImageService;
import com.domain.newsletter.application.NewsletterLikeService;
import com.domain.newsletter.application.NewsletterPostService;
import com.domain.newsletter.application.NewsletterScrapService;
import com.domain.newsletter.application.NewsletterUrlService;
import com.domain.newsletter.application.request.NewsletterCreate;
import com.api.newsletter.presentation.response.NewsCategoryPagingResponse;
import com.api.newsletter.presentation.response.NewsletterSingleResponse;
import com.domain.newsletter.application.response.NewsletterThumbnailResponse;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterComment;
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
    private final Uploader uploader;
    private final MemberService memberService;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final NewsletterWebCreate request, final Long currentMemberId) {
        String mainImageUrl = uploadImage(request.getMainImage());

        Member currentMember = memberService.getById(currentMemberId);

        NewsletterCreate create = NewsletterCreate.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .mainImageUrl(mainImageUrl)
                .member(currentMember)
                .build();

        Newsletter newsletter = Newsletter.from(create);
        postService.saveNewsletter(newsletter);

        List<MultipartFile> images = request.getSubImages();
        if (images != null && !images.isEmpty()) {
            uploadImages(newsletter, images);
        }

        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            registerUrls(newsletter, urls);
        }

        return Message.registerPostSuccess(Newsletter.class, newsletter.getId());
    }

    private String uploadImage(final MultipartFile image) {
        return uploader.uploadMultipartFile(image);
    }

    private void uploadImages(final Newsletter newsletter, final List<MultipartFile> uploadImages) {
        List<String> uploadUrls = uploader.uploadMultipartFiles(uploadImages);
        uploadUrls.forEach(uploadUrl -> imageService.register(newsletter, uploadUrl));
    }

    private void registerUrls(final Newsletter newsletter, final List<String> urls) {
        urls.forEach(url -> urlService.register(newsletter, url));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */

    @Transactional
    public Message delete(final Long postId, final Long currentMemberId) {
        Newsletter newsletter = postService.getNewsletter(postId);
        Member currentMember = memberService.getById(currentMemberId);
        checkManagerPermission(currentMember);
        newsletter.deactivate();
        return Message.deletePostSuccess(Newsletter.class, postId);
    }

    private void checkManagerPermission(final Member currentMember) {
        if (!currentMember.getRole().equals(Role.MANAGER)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_ADMIN_DELETE_POST);
        }
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- 단건 READ -------------------------------------------- */

    public NewsletterSingleResponse getSinglePost(final Long postId, final Long currentMemberId) {
        Newsletter newsletterWithImg = postService.findNewsletterWithDetails(postId);
        List<NewsletterComment> activeComments = commentService.findCommentsByNewsletterId(postId);
        int likeCount = likeService.countNewsletterLikes(postId).intValue();
        int scrapCount = scrapService.countNewsletterScraps(postId).intValue();

        boolean likedByCurrentUser = likeService.isNewsletterExists(postId, currentMemberId);
        boolean scrapedByCurrentUser = scrapService.isNewsletterExists(postId, currentMemberId);

        NewsletterSingleResponse response = NewsletterSingleResponse.toDto(newsletterWithImg,
                activeComments, likeCount, scrapCount, likedByCurrentUser, scrapedByCurrentUser);
        return response;
    }

    /* -------------------------------------------- 단건 READ 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long postId, final ReportWebCreate request, final Long reporterId) {
        Newsletter newsletter = postService.getNewsletter(postId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        Member reporter = memberService.getById(reporterId);
        reportService.reportNewsletter(create, newsletter, reporter);
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

}
