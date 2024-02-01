package com.api.ttoklip.domain.honeytip.post.post.service;

import com.api.ttoklip.domain.honeytip.post.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneyTipCreateReq;
import com.api.ttoklip.domain.honeytip.post.post.editor.HoneyTipPostEditor;
import com.api.ttoklip.domain.honeytip.post.post.repository.HoneyTipRepository;
import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipPostService {
    private final HoneyTipRepository honeytipRepository;
    private final S3FileUploader s3FileUploader;

    @Transactional
    public Long register(final HoneyTipCreateReq request) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());

        // HoneytipImage 객체 생성
        List<HoneyTipImage> honeyTipImages = imageUrls.stream()
                .map(url -> HoneyTipImage.builder()
                        .url(url)
                        .honeytip(null)
                        .build())
                .toList();

        //HoneytipUrl 객체 생성
        List<HoneyTipUrl> honeyTipUrls = request.getUrl().stream()
                .map(url -> HoneyTipUrl.builder()
                        .url(url)
                        .honeyTip(null)
                        .build())
                .toList();

        // Honeytip 객체 생성 및 연관 관계 설정
        HoneyTip honeytip = HoneyTip.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .honeyTipImageList(honeyTipImages)
                .honeyTipUrlList(honeyTipUrls)
                .build();

        honeyTipImages.forEach(image -> image.updateHoneyTip(honeytip));
        honeyTipUrls.forEach(url -> url.updateHoneyTip(honeytip));

        // 엔티티에 저장
        honeytipRepository.save(honeytip);

        // 작성된 꿀팁의 id 값 리턴
        return honeytip.getId();
    }

    public Long edit(final Long postId, final HoneyTipCreateReq request) {

        // 기존 게시글 찾기
        HoneyTip existingHoneyTip = getHoneytip(postId);

        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());

        // HoneytipImage 객체 생성
        List<HoneyTipImage> honeyTipImages = imageUrls.stream()
                .map(url -> HoneyTipImage.builder()
                        .url(url)
                        .honeytip(null)
                        .build())
                .toList();

        //HoneytipUrl 객체 생성
        List<HoneyTipUrl> honeyTipUrls = request.getUrl().stream()
                .map(url -> HoneyTipUrl.builder()
                        .url(url)
                        .honeyTip(null)
                        .build())
                .toList();

        // HoneytipPostEditor 객체 생성 및 연관 관계 설정
        HoneyTipPostEditor editor = HoneyTipPostEditor.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .honeyTipImageList(honeyTipImages)
                .honeyTipUrlList(honeyTipUrls)
                .build();

        // 꿀팁 업데이트
        editor.applyTo(existingHoneyTip);

        // 작성된 꿀팁의 id 값 리턴
        return existingHoneyTip.getId();
    }

    private HoneyTip getHoneytip(final Long postId) {
        return honeytipRepository.findByIdActivated(postId);
    }

    public Message delete(final Long postId) {

        // 유저 정보 찾기

        // 저장된 게시글 찾기
        HoneyTip honeytip = getHoneytip(postId);

        honeytipRepository.delete(honeytip);

        return Message.builder()
                .message("게시글 삭제에 성공했습니다.")
                .build();
    }
}
