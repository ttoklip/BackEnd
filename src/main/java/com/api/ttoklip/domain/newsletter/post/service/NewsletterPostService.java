package com.api.ttoklip.domain.newsletter.post.service;

import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.image.service.NewsletterImageService;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.repository.NewsletterRepository;
import com.api.ttoklip.domain.newsletter.post.dto.request.NewsletterCreateReq;
import com.api.ttoklip.domain.newsletter.post.dto.response.NewsletterWithCommentRes;
import com.api.ttoklip.domain.newsletter.url.service.NewsletterUrlService;
import com.api.ttoklip.global.s3.S3FileUploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public Long register(final NewsletterCreateReq request) {

        Newsletter newsletter = Newsletter.of(request);
        newsletterRepository.save(newsletter);

        List<MultipartFile> images = request.getImages();
        if (images != null && !images.isEmpty()) {
            registerImages(newsletter, images);
        }

        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            registerUrls(newsletter, urls);
        }

        // 작성한 뉴스레터의 id 값 리턴
        return newsletter.getId();
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

    // 뉴스레터 - 글 개별 상세 조회(READ)
    public NewsletterWithCommentRes getSinglePost(final Long postId) {
        Newsletter newsletter = newsletterRepository.findByIdFetchJoin(postId);
        List<NewsletterComment> activeComments = newsletterRepository.findActiveCommentsByNewsletterId(postId);
        NewsletterWithCommentRes newsletterWithCommentRes = NewsletterWithCommentRes.toDto(newsletter, activeComments);
        return newsletterWithCommentRes;
    }

}
