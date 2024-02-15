package com.api.ttoklip.domain.town.community.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.service.CommunityImageService;
import com.api.ttoklip.domain.town.community.like.service.CommunityLikeService;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunitySingleResponse;
import com.api.ttoklip.domain.town.community.post.editor.CommunityPostEditor;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.domain.town.community.scrap.service.CommunityScrapService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    public static Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

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

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- READ -------------------------------------------- */

    private void registerImages(final Community community, final List<MultipartFile> uploadImages) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> uploadUrls = communityCommonService.uploadImages(uploadImages);
        uploadUrls.forEach(uploadUrl -> communityImageService.register(community, uploadUrl));
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */

    /* -------------------------------------------- EDIT -------------------------------------------- */

    public CommunitySingleResponse getSinglePost(final Long postId) {

        Community communityWithImg = communityRepository.findByIdFetchJoin(postId);
        List<CommunityComment> activeComments = communityRepository.findActiveCommentsByCommunityId(postId);
        int likeCount = communityLikeService.countCommunityLikes(postId).intValue();
        int scrapCount = communityScrapService.countCommunityScraps(postId).intValue();

        boolean likedByCurrentUser = communityLikeService.existsByNewsletterIdAndMemberId(postId);

        CommunitySingleResponse communitySingleResponse = CommunitySingleResponse.of(communityWithImg,
                activeComments, likeCount, scrapCount, likedByCurrentUser);
        return communitySingleResponse;
    }

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

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */

    private void editImages(final List<MultipartFile> multipartFiles, final Community community) {
        Long communityId = community.getId();
        // 기존 이미지 전부 제거
        communityImageService.deleteAllByPostId(communityId);

        // 새로운 이미지 업로드
        List<String> uploadUrls = communityCommonService.uploadImages(multipartFiles);
        uploadUrls.forEach(uploadUrl -> communityImageService.register(community, uploadUrl));
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- Soft Delete -------------------------------------------- */
//    public void delete(final Long postId) {
//        Community community = findCommunity(postId);
//        community.deactivate(); // 비활성화
//    }


    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */

    @Transactional
    public Message delete(final Long postId) {
        Community community = communityCommonService.getCommunity(postId);

        // 삭제 권한 확인
        communityCommonService.checkEditPermission(community);
        community.deactivate();

        return Message.deletePostSuccess(Community.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

//    @Transactional
//    public Message like(final Long postId) {
//        communityLikeService.register(postId);
//        return Message.likePostSuccess(Community.class, postId);
//    }

    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        Community community = communityCommonService.getCommunity(postId);
        reportService.reportCommunity(request, community);

        return Message.reportPostSuccess(Community.class, postId);
    }

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

}
