package com.api.ttoklip.domain.town.community.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.service.CommunityImageService;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunitySingleResponse;
import com.api.ttoklip.domain.town.community.post.editor.CommunityPostEditor;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostService {

    private final CommunityRepository communityRepository;
    private final CommunityImageService communityImageService;
    private final S3FileUploader s3FileUploader;
    private final ReportService reportService;


    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Community findCommunityById(final Long postId) {
        return communityRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUND));
    }

    public Community getCommunity(final Long postId) {
        return communityRepository.findByIdActivated(postId);
    }

    private List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return s3FileUploader.uploadMultipartFiles(uploadImages);
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final CommunityCreateRequest request) {

        Community community = Community.of(request);
        communityRepository.save(community);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(community, uploadImages);
        }

        return community.getId();
    }

    private void registerImages(final Community community, final List<MultipartFile> multipartFiles) {
        List<String> uploadUrls = getImageUrls(multipartFiles);
        uploadUrls.forEach(uploadUrl -> communityImageService.register(community, uploadUrl));
    }

    private List<String> getImageUrls(final List<MultipartFile> multipartFiles) {
        return s3FileUploader.uploadMultipartFiles(multipartFiles);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- READ -------------------------------------------- */
    public CommunitySingleResponse getSinglePost(final Long postId) {

        Community communityWithImg = communityRepository.findByIdFetchJoin(postId);
        List<CommunityComment> activeComments = communityRepository.findActiveCommentsByCommunityId(postId);
        CommunitySingleResponse communitySingleResponse = CommunitySingleResponse.of(communityWithImg, activeComments);
        return communitySingleResponse;
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */
    @Transactional
    public Message edit(final Long postId, final CommunityCreateRequest request) {

        Community community = getCommunity(postId);

        CommunityPostEditor postEditor = getPostEditor(request, community);
        community.edit(postEditor);

        List<MultipartFile> images = request.getImages();
        if (images != null && !images.isEmpty()) {
            editImages(images, community);
        }

        return Message.editPostSuccess(Community.class, community.getId());
    }

    private CommunityPostEditor getPostEditor(final CommunityCreateRequest request, final Community community) {
        CommunityPostEditor.CommunityPostEditorBuilder editorBuilder = community.toEditor();
        CommunityPostEditor communityPostEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return communityPostEditor;
    }

    private void editImages(final List<MultipartFile> multipartFiles, final Community community) {
        Long communityId = community.getId();
        // 기존 이미지 전부 제거
        communityImageService.deleteAllByPostId(communityId);

        // 새로운 이미지 업로드
        List<String> uploadUrls = uploadImages(multipartFiles);
        uploadUrls.forEach(uploadUrl -> communityImageService.register(community, uploadUrl));
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- Soft Delete -------------------------------------------- */
    public void delete(final Long postId) {
        Community community = findCommunity(postId);
        community.deactivate(); // 비활성화
    }

    private Community findCommunity(final Long postId) {
        return communityRepository.findByIdUnDeleted(postId);
    }

    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public void report(final Long postId, final ReportCreateRequest request) {
        Community community = findCommunityById(postId);
        reportService.reportCommunity(request, community);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */
}
