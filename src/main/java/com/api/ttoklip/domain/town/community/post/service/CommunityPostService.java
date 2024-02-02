package com.api.ttoklip.domain.town.community.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.town.community.image.service.ImageService;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunitySearchCondition;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityUpdateRequest;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunityListResponse;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunityResponse;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final CommunityRepository communityRepository;
    private final ImageService imageService;
    private final S3FileUploader s3FileUploader;
    private final ReportService reportService;

    /* -------------------------------------------- CREATE -------------------------------------------- */
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
        uploadUrls.forEach(uploadUrl -> imageService.register(community, uploadUrl));
    }

    private List<String> getImageUrls(final List<MultipartFile> multipartFiles) {
        return s3FileUploader.uploadMultipartFiles(multipartFiles);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    public CommunityWithCommentResponse getSinglePost(final Long postId) {
        return null;
    }

    /* -------------------------------------------- REPORT -------------------------------------------- */

    public void report(final Long postId, final ReportCreateRequest request) {
        Community community = communityRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUNT));
        reportService.reportCommunity(request, community);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

    /* -------------------------------------------- Soft Delete -------------------------------------------- */

    public void delete(final Long postId) {
        Community community = findCommunity(postId);
        community.deactivate(); // 비활성화
    }

    private Community findCommunity(final Long postId) {
        return communityRepository.findByIdUndeleted(postId);
    }

    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */

}
