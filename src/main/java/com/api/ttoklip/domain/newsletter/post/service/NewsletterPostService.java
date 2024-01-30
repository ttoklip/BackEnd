package com.api.ttoklip.domain.newsletter.post.service;

import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneytipCreateReq;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.NewsletterImage;
import com.api.ttoklip.domain.newsletter.post.domain.NewsletterUrl;
import com.api.ttoklip.domain.newsletter.post.domain.repository.NewsletterRepository;
import com.api.ttoklip.domain.newsletter.post.dto.request.NewsletterCreateReq;
import com.api.ttoklip.domain.newsletter.post.dto.response.NewsletterWithCommentRes;
import com.api.ttoklip.global.s3.S3FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterPostService {

    private final NewsletterRepository newsletterRepository;
    private final S3FileUploader s3FileUploader;


    public Long register(NewsletterCreateReq request) {

        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());

        // NewsletterImage 객체 생성
        List<NewsletterImage> newsletterImages = imageUrls.stream()
                .map(url -> new NewsletterImage(url, null))
                .collect(Collectors.toList());

        // NewsletterUrl 객체 생성
        List<NewsletterUrl> newsletterUrls = request.getUrl().stream()
                .map(url -> new NewsletterUrl(url, null))
                .collect(Collectors.toList());

        // Newsletter 객체 생성 및 연관 관계 설정
        Newsletter newsletter = Newsletter.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .honeytipImageList(newsletterImages)
                .honeytipUrlList(newsletterUrls)
                .build();

        newsletterImages.forEach(image -> image.updateNewsletter(newsletter));
        newsletterUrls.forEach(url -> url.updateNewsletter(newsletter));

        // 엔티티에 저장
        newsletterRepository.save(newsletter);

        // 작성한 뉴스레터의 id 값 리턴
        return newsletter.getId();
    }

    public NewsletterWithCommentRes getSinglePost(Long postId) {
        return null;
    }

}
