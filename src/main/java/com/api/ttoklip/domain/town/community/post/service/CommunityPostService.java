package com.api.ttoklip.domain.town.community.post.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.filtering.aop.annotation.CheckBadWordCreate;
import com.api.ttoklip.domain.common.filtering.aop.annotation.CheckBadWordUpdate;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.notification.aop.annotation.SendNotification;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.service.CommunityImageService;
import com.api.ttoklip.domain.town.community.like.service.CommunityLikeService;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityEditReq;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunityRecent3Response;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunitySingleResponse;
import com.api.ttoklip.domain.town.community.post.editor.CommunityPostEditor;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.domain.town.community.scrap.service.CommunityScrapService;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostService {
    private final CommunityRepository communityRepository;

    private final CommunityImageService communityImageService;
    private final ReportService reportService;
    private final CommunityLikeService communityLikeService;
    private final CommunityCommonService communityCommonService;
    private final CommunityScrapService communityScrapService;


    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    @CheckBadWordCreate
    public Message register(final CommunityCreateRequest request) {
        Member currentMember = getCurrentMember();

        Community community = Community.of(request, currentMember);
        communityRepository.save(community);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(community, uploadImages);
        }

        return Message.registerPostSuccess(Community.class, community.getId());
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- READ -------------------------------------------- */

    private void registerImages(final Community community, final List<MultipartFile> uploadImages) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> uploadUrls = communityCommonService.uploadImages(uploadImages);
        uploadUrls.forEach(uploadUrl -> communityImageService.register(community, uploadUrl));
    }

    public CommunitySingleResponse getSinglePost(final Long postId) {

        Community communityWithImg = communityRepository.findByIdFetchJoin(postId);
        List<CommunityComment> activeComments = communityRepository.findActiveCommentsByCommunityId(postId);
        int likeCount = communityLikeService.countCommunityLikes(postId).intValue();
        int scrapCount = communityScrapService.countCommunityScraps(postId).intValue();

        boolean likedByCurrentUser = communityLikeService.existsByCommunityIdAndMemberId(postId);
        boolean scrapedByCurrentUser = communityScrapService.existsByCommunityIdAndMemberId(postId);

        CommunitySingleResponse communitySingleResponse = CommunitySingleResponse.of(communityWithImg,
                activeComments, likeCount, scrapCount, likedByCurrentUser, scrapedByCurrentUser);
        return communitySingleResponse;
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */

    @Transactional
    @CheckBadWordUpdate
    public Message edit(final Long postId, final CommunityEditReq request) {

        // 기존 게시글 찾기
        Community community = communityCommonService.getCommunity(postId);

        // 삭제 권한 확인
        communityCommonService.checkEditPermission(community);

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

    private CommunityPostEditor getPostEditor(final CommunityEditReq request, final Community community) {
        CommunityPostEditor.CommunityPostEditorBuilder editorBuilder = community.toEditor();
        CommunityPostEditor communityPostEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return communityPostEditor;
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */

    private void deleteImages(List<Long> deleteImageIds) {
        communityImageService.deleteImages(deleteImageIds);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- Soft Delete -------------------------------------------- */
    @Transactional
    public Message delete(final Long postId) {
        Community community = communityCommonService.getCommunity(postId);

        // 삭제 권한 확인
        communityCommonService.checkEditPermission(community);
        community.deactivate();

        return Message.deletePostSuccess(Community.class, postId);
    }

    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        Community community = communityCommonService.getCommunity(postId);
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

    public List<CommunityRecent3Response> getRecent3() {
        List<Community> communities = communityRepository.getRecent3();
        return communities.stream()
                .map(CommunityRecent3Response::of)
                .toList();
    }

    /* -------------------------------------------- SCRAP 끝 -------------------------------------------- */


    /* -------------------------------------------- Community 페이징 -------------------------------------------- */
    public Page<Community> getPaging(final Pageable pageable) {
        return communityRepository.getPaging(pageable);
    }
    /* -------------------------------------------- Community 끝 -------------------------------------------- */
}
