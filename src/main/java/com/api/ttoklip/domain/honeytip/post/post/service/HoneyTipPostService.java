package com.api.ttoklip.domain.honeytip.post.post.service;

import com.api.ttoklip.domain.honeytip.post.image.service.HoneyTipImageService;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneyTipCreateReq;
import com.api.ttoklip.domain.honeytip.post.post.repository.HoneyTipRepository;
import com.api.ttoklip.domain.honeytip.url.HoneyTipUrlService;
import com.api.ttoklip.global.s3.S3FileUploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipPostService {
    private final HoneyTipRepository honeytipRepository;
    private final HoneyTipImageService honeyTipImageService;
    private final HoneyTipUrlService honeyTipUrlService;
    private final S3FileUploader s3FileUploader;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Long register(final HoneyTipCreateReq request) {

        // Honeytip 객체 생성 및 연관 관계 설정
        HoneyTip honeytip = HoneyTip.from(request);
        honeytipRepository.save(honeytip);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(honeytip, uploadImages);
        }

        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            registerUrls(honeytip, urls);
        }

        return honeytip.getId();
    }

    private void registerImages(final HoneyTip honeytip, final List<MultipartFile> uploadImages) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> uploadUrls = uploadImages(uploadImages);
        uploadUrls.forEach(uploadUrl -> honeyTipImageService.register(honeytip, uploadUrl));
    }

    private List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return s3FileUploader.uploadMultipartFiles(uploadImages);
    }

    private void registerUrls(final HoneyTip honeytip, final List<String> urls) {
        urls.forEach(url -> honeyTipUrlService.register(honeytip, url));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

//    public Long edit(final Long postId, final HoneyTipCreateReq request) {
//
//        // 기존 게시글 찾기
//        HoneyTip existingHoneyTip = getHoneytip(postId);
//
//        // S3에 이미지 업로드 후 URL 목록을 가져온다.
//        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());
//
//        // HoneytipImage 객체 생성
//        List<HoneyTipImage> honeyTipImages = imageUrls.stream()
//                .map(url -> HoneyTipImage.builder()
//                        .url(url)
//                        .honeytip(null)
//                        .build())
//                .toList();
//
//        //HoneytipUrl 객체 생성
//        List<HoneyTipUrl> honeyTipUrls = request.getUrl().stream()
//                .map(url -> HoneyTipUrl.builder()
//                        .url(url)
//                        .honeyTip(null)
//                        .build())
//                .toList();
//
//        // HoneytipPostEditor 객체 생성 및 연관 관계 설정
//        HoneyTipPostEditor editor = HoneyTipPostEditor.builder()
//                .title(request.getTitle())
//                .content(request.getContent())
//                .category(request.getCategory())
//                .honeyTipImageList(honeyTipImages)
//                .honeyTipUrlList(honeyTipUrls)
//                .build();
//
//        // 꿀팁 업데이트
//        editor.applyTo(existingHoneyTip);
//
//        // 작성된 꿀팁의 id 값 리턴
//        return existingHoneyTip.getId();
//    }
//
//    private HoneyTip getHoneytip(final Long postId) {
//        return honeytipRepository.findByIdActivated(postId);
//    }
//
//    public Message delete(final Long postId) {
//
//        // 유저 정보 찾기
//
//        // 저장된 게시글 찾기
//        HoneyTip honeytip = getHoneytip(postId);
//
//        honeytipRepository.delete(honeytip);
//
//        return Message.builder()
//                .message("게시글 삭제에 성공했습니다.")
//                .build();
//    }
}
