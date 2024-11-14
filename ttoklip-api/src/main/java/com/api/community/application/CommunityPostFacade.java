package com.api.community.application;

import com.api.common.ReportWebCreate;
import com.api.common.upload.Uploader;
import com.api.community.presentation.dto.request.CommunityWebCreate;
import com.api.community.presentation.dto.request.CommunityWebEdit;
import com.api.community.presentation.dto.response.CommunityResponse;
import com.api.global.success.Message;
import com.common.annotation.FilterBadWord;
import com.common.annotation.DistributedLock;
import com.common.annotation.SendNotification;
import com.common.NotiCategory;
import com.domain.report.domain.ReportCreate;
import com.domain.report.application.ReportService;
import com.domain.common.vo.TownCriteria;
import com.domain.community.application.CommunityCommentService;
import com.domain.community.application.CommunityImageService;
import com.domain.community.application.CommunityLikeService;
import com.domain.community.application.CommunityPostService;
import com.domain.community.application.CommunityScrapService;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityComment;
import com.domain.community.domain.CommunityCreate;
import com.domain.community.domain.CommunityEdit;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    @FilterBadWord
    @DistributedLock(keyPrefix = "community-")
    public Message register(final CommunityWebCreate request, final Long currentMemberId) {
        Member member = memberService.getById(currentMemberId);
        CommunityCreate create = CommunityCreate.of(request.getTitle(), request.getContent(), member);
        Community community = Community.from(create);
        communityPostService.save(community);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(community, uploadImages);
        }

        return Message.registerPostSuccess(Community.class, community.getId());
    }

    private void registerImages(final Community community, final List<MultipartFile> uploadImages) {
        List<String> uploadUrls = uploader.uploadMultipartFiles(uploadImages);
        uploadUrls.forEach(uploadUrl -> communityImageService.register(community, uploadUrl));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    /* -------------------------------------------- READ -------------------------------------------- */

    public CommunityResponse getSinglePost(final Long postId, Long currentMemberId) {
        Community communityWithImg = communityPostService.findByIdFetchJoin(postId);
        List<CommunityComment> activeComments = communityCommentService.findCommentsByCommunityId(postId);

        int likeCount = communityLikeService.countCommunityLikes(postId).intValue();
        int scrapCount = communityScrapService.countCommunityScraps(postId).intValue();

        boolean likedByCurrentUser = communityLikeService.existsByCommunityIdAndMemberId(postId, currentMemberId);
        boolean scrapedByCurrentUser = communityScrapService.existsByCommunityIdAndMemberId(postId, currentMemberId);

        return CommunityResponse.of(communityWithImg, activeComments, likeCount, scrapCount, likedByCurrentUser,
                scrapedByCurrentUser);
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */

    /* -------------------------------------------- EDIT -------------------------------------------- */

    @Transactional
    @FilterBadWord
    public Message edit(final Long postId, final CommunityWebEdit request, final Long memberId) {
        Community community = communityPostService.getCommunity(postId);

        CommunityEdit edit = CommunityEdit.of(request.getTitle(), request.getContent());
        communityPostService.edit(edit, community, memberId);

        List<MultipartFile> addImages = request.getAddImages();
        if (addImages != null && !addImages.isEmpty()) {
            registerImages(community, addImages);
        }

        List<Long> deleteImageIds = request.getDeleteImageIds();
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            deleteImages(deleteImageIds, memberId);
        }

        return Message.editPostSuccess(Community.class, community.getId());
    }

    private void deleteImages(List<Long> deleteImageIds, Long memberId) {
        communityImageService.deleteImages(deleteImageIds, memberId);
    }


    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */

    /* -------------------------------------------- DELETE -------------------------------------------- */

    @Transactional
    public Message delete(final Long postId, final Long memberId) {
        Community community = communityPostService.getCommunity(postId);
        communityPostService.delete(community, memberId);
        return Message.deletePostSuccess(Community.class, postId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long postId, final ReportWebCreate request, final Long reporterId) {
        Community community = communityPostService.getCommunity(postId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        Member reporter = memberService.getById(reporterId);
        reportService.reportCommunity(create, community, reporter);
        return Message.reportPostSuccess(Community.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

    /* -------------------------------------------- LIKE -------------------------------------------- */

    @Transactional
    public Message registerLike(final Long postId, final Long memberId) {
        Member member = memberService.getById(memberId);
        Community community = communityPostService.getCommunity(postId);
        communityLikeService.register(community, member);
        return Message.likePostSuccess(Community.class, postId);
    }

    @Transactional
    public Message cancelLike(final Long postId, final Long memberId) {
        Community community = communityPostService.getCommunity(postId);
        communityLikeService.cancel(community.getId(), memberId);
        return Message.likePostCancel(Community.class, postId);
    }

    /* -------------------------------------------- LIKE 끝 -------------------------------------------- */

    /* -------------------------------------------- SCRAP -------------------------------------------- */

    @Transactional
    @SendNotification(notiCategory = NotiCategory.OUR_TOWN_SCRAP)
    public Message registerScrap(final Long postId, final Long memberId) {
        Community community = communityPostService.getCommunity(postId);
        Member member = memberService.getById(memberId);
        communityScrapService.registerScrap(community, member);
        return Message.scrapPostSuccess(Community.class, postId);
    }

    @Transactional
    public Message cancelScrap(final Long postId, final Long memberId) {
        Community community = communityPostService.getCommunity(postId);
        communityScrapService.cancelScrap(community.getId(), memberId);
        return Message.scrapPostCancel(Community.class, postId);
    }

    /* -------------------------------------------- SCRAP 끝 -------------------------------------------- */

    /* -------------------------------------------- Community 페이징 -------------------------------------------- */

    public Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable) {
        return communityPostService.getPaging(townCriteria, pageable);
    }

    /* -------------------------------------------- Community 끝 -------------------------------------------- */
}
