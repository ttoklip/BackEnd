package com.api.ttoklip.domain.town.community.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.service.CommunityImageService;
import com.api.ttoklip.domain.town.community.like.repository.CommunityLikeRepository;
import com.api.ttoklip.domain.town.community.like.service.CommunityLikeService;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunitySingleResponse;
import com.api.ttoklip.domain.town.community.post.editor.CommunityPostEditor;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import jakarta.activation.CommandMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.api.ttoklip.domain.town.community.like.service.CommunityLikeService.getCurrentMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostService {
    private final CommunityRepository communityRepository;

    private final CommunityImageService communityImageService;
    private final ReportService reportService;
    private final CommunityLikeRepository communityLikeRepository;
    private final CommunityLikeService communityLikeService;
    private final CommunityCommonService communityCommonService;


    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    public Message register(final CommunityCreateRequest request) {

        // Community 객체 생성 및 연관 관계 설정

        Member currentMember = getCurrentMember();

        Community community = Community.of(request, currentMember);
        communityRepository.save(community);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(community, uploadImages);
        }

        return Message.registerPostSuccess(Community.class, community.getId());
    }

    private void registerImages(final Community community, final List<MultipartFile> uploadImages) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> uploadUrls = communityCommonService.uploadImages(uploadImages);
        uploadUrls.forEach(uploadUrl -> communityImageService.register(community, uploadUrl));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- READ -------------------------------------------- */

    public CommunitySingleResponse getSinglePost(final Long postId) {

        Community communityWithImg = communityRepository.findByIdFetchJoin(postId);
        List<CommunityComment> activeComments = communityRepository.findActiveCommentsByCommunityId(postId);

        // todo 현재 사용자가 좋아요를 눌렀는지 확인

        CommunitySingleResponse communitySingleResponse = CommunitySingleResponse.of(communityWithImg, activeComments);
        return communitySingleResponse;
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */

    /* -------------------------------------------- EDIT -------------------------------------------- */

    @Transactional
    public Message edit(final Long postId, final CommunityCreateRequest request) {

        // 기존 게시글 찾기
        Community community = communityCommonService.getCommunity(postId);

        // 삭제 권한 확인
        communityCommonService.checkEditPermission(community);

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

    /* -------------------------------------------- DELETE -------------------------------------------- */

    @Transactional
    public Message delete(final Long postId) {
        Community community = communityCommonService.getCommunity(postId);

        // 삭제 권한 확인
        communityCommonService.checkEditPermission(community);
        community.deactivate();

        return Message.deletePostSuccess(Community.class, postId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- Soft Delete -------------------------------------------- */
//    public void delete(final Long postId) {
//        Community community = findCommunity(postId);
//        community.deactivate(); // 비활성화
//    }


    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        Community community = findCommunityById(postId);
        reportService.reportCommunity(request, community);

        return Message.reportPostSuccess(Community.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

    @Transactional
    public Message like(final Long postId) {
        communityLikeService.register(postId);
        return Message.likePostSuccess(Community.class, postId);
    }

    public static Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }
}
