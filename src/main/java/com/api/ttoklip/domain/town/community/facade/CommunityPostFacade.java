package com.api.ttoklip.domain.town.community.facade;

import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordCreate;
import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordUpdate;
import com.api.ttoklip.domain.aop.notification.annotation.SendNotification;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.community.controller.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.controller.dto.request.CommunityEditReq;
import com.api.ttoklip.domain.town.community.controller.dto.response.CommunityRecent3Response;
import com.api.ttoklip.domain.town.community.controller.dto.response.CommunitySingleResponse;
import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.domain.town.community.domain.CommunityComment;
import com.api.ttoklip.domain.town.community.editor.CommunityPostEditor;
import com.api.ttoklip.domain.town.community.service.*;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.upload.Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostFacade {

    private final CommunityPostService communityPostService;
    private final MemberService memberService;
    private final CommunityImageService communityImageService;
    private final ReportService reportService;
    private final CommunityLikeService communityLikeService;
    private final CommunityScrapService communityScrapService;
    private final Uploader uploader;
    private final CommunityCommentService communityCommentService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    @CheckBadWordCreate
    public Message register(final CommunityCreateRequest request, final Long currentMemberId) {
        Member currentMember = memberService.findById(currentMemberId);
        Community community = Community.of(request, currentMember);
        communityPostService.saveCommunityPost(community);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(community, uploadImages);
        }

        return Message.registerPostSuccess(Community.class, community.getId());
    }

    private void registerImages(final Community community, final List<MultipartFile> uploadImages) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> uploadUrls = uploader.uploadMultipartFiles(uploadImages);
        uploadUrls.forEach(uploadUrl -> communityImageService.register(community, uploadUrl));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- READ -------------------------------------------- */

    public CommunitySingleResponse getSinglePost(final Long postId, Long currentMemberId) {
        Community communityWithImg = communityPostService.findByIdFetchJoin(postId);
        List<CommunityComment> activeComments = communityCommentService.findCommentsByCommunityId(postId);

        int likeCount = communityLikeService.countCommunityLikes(postId).intValue();
        int scrapCount = communityScrapService.countCommunityScraps(postId).intValue();

        boolean likedByCurrentUser = communityLikeService.existsByCommunityIdAndMemberId(postId, currentMemberId);
        boolean scrapedByCurrentUser = communityScrapService.existsByCommunityIdAndMemberId(postId, currentMemberId);

        CommunitySingleResponse communitySingleResponse = CommunitySingleResponse.of(communityWithImg,
                activeComments, likeCount, scrapCount, likedByCurrentUser, scrapedByCurrentUser);
        return communitySingleResponse;
    }

    public List<CommunityRecent3Response> getRecent3(final TownCriteria townCriteria) {
        return communityPostService.getRecent3(townCriteria);
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */

    /* -------------------------------------------- EDIT -------------------------------------------- */

    @Transactional
    @CheckBadWordUpdate
    public Message edit(final Long postId, final CommunityEditReq request) {

        // 기존 게시글 찾기
        Community community = communityPostService.getCommunity(postId);

        // 수정 권한 확인
        checkEditPermission(community);

        CommunityPostEditor postEditor = getPostEditor(request, community);
        community.edit(postEditor);

        List<MultipartFile> addImages = request.getAddImages();
        if (addImages != null && !addImages.isEmpty()) {
            registerImages(community, addImages);
        }

        List<Long> deleteImageIds = request.getDeleteImageIds();
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            deleteImages(deleteImageIds);
        }

        return Message.editPostSuccess(Community.class, community.getId());
    }

    private void deleteImages(List<Long> deleteImageIds) {
        communityImageService.deleteImages(deleteImageIds);
    }

    private CommunityPostEditor getPostEditor(final CommunityEditReq request, final Community community) {
        CommunityPostEditor.CommunityPostEditorBuilder editorBuilder = community.toEditor();
        CommunityPostEditor communityPostEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return communityPostEditor;
    }

    private void checkEditPermission(final Community community) {
        Long writerId = community.getMember().getId();
        Long currentMemberId = getCurrentMember().getId();

        if (!writerId.equals(currentMemberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */

    @Transactional
    public Message delete(final Long postId) {
        Community community = communityPostService.getCommunity(postId);

        // 삭제 권한 확인
        checkEditPermission(community);
        community.deactivate();

        return Message.deletePostSuccess(Community.class, postId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        Community community = communityPostService.getCommunity(postId);
        reportService.reportCommunity(request, community);

        return Message.reportPostSuccess(Community.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

    /* -------------------------------------------- LIKE -------------------------------------------- */

    @Transactional
    public Message registerLike(Long postId) {
        communityLikeService.register(postId);
        return Message.likePostSuccess(Community.class, postId);
    }

    @Transactional
    public Message cancelLike(Long postId) {
        communityLikeService.cancel(postId);
        return Message.likePostCancel(Community.class, postId);
    }

    /* -------------------------------------------- LIKE 끝 -------------------------------------------- */

    /* -------------------------------------------- SCRAP -------------------------------------------- */

    @Transactional
    @SendNotification
    public Message registerScrap(Long postId) {
        communityScrapService.registerScrap(postId);
        return Message.scrapPostSuccess(Community.class, postId);
    }

    @Transactional
    public Message cancelScrap(Long postId) {
        communityScrapService.cancelScrap(postId);
        return Message.scrapPostCancel(Community.class, postId);
    }

    /* -------------------------------------------- SCRAP 끝 -------------------------------------------- */

    /* -------------------------------------------- Community 페이징 -------------------------------------------- */

    public Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable) {
        return communityPostService.getPaging(townCriteria, pageable);
    }

    /* -------------------------------------------- Community 끝 -------------------------------------------- */
}
