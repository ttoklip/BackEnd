package com.api.honeytip.application;

import com.api.common.ReportWebCreate;
import com.api.common.upload.MultipartFileAdapter;
import com.infrastructure.aws.upload.FileInput;
import com.infrastructure.aws.upload.Uploader;
import com.api.global.success.Message;
import com.api.honeytip.presentation.request.HoneyTipWebCreate;
import com.api.honeytip.presentation.request.HoneyTipWebEdit;
import com.api.honeytip.presentation.response.HoneyTipSingleResponse;
import com.common.annotation.DistributedLock;
import com.domain.report.application.ReportService;
import com.domain.report.domain.ReportCreate;
import com.domain.honeytip.application.HoneyTipCommentService;
import com.domain.honeytip.application.HoneyTipImageService;
import com.domain.honeytip.application.HoneyTipLikeService;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.application.HoneyTipScrapService;
import com.domain.honeytip.application.HoneyTipUrlService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.honeytip.domain.HoneyTipEditor;
import com.domain.honeytip.domain.request.HoneyTipCreate;
import com.domain.honeytip.domain.request.HoneyTipEdit;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.common.annotation.FilterBadWord;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipPostFacade {

    private final HoneyTipPostService honeyTipPostService;
    private final HoneyTipCommentService honeyTipCommentService;

    private final HoneyTipImageService honeyTipImageService;
    private final HoneyTipScrapService honeyTipScrapService;
    private final HoneyTipLikeService honeyTipLikeService;
    private final HoneyTipUrlService honeyTipUrlService;

    private final ReportService reportService;

    private final MemberService memberService;

    private final Uploader uploader;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    @FilterBadWord
    @DistributedLock(keyPrefix = "honeytip-")
    public Message register(final HoneyTipWebCreate request, final Long currentMemberId) {
        Member currentMember = memberService.getById(currentMemberId);
        HoneyTipCreate honeyTipCreate = HoneyTipCreate.of(request.getTitle(), request.getContent(), request.getCategory());

        HoneyTip honeytip = HoneyTip.of(honeyTipCreate, currentMember);
        honeyTipPostService.save(honeytip);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(honeytip, uploadImages);
        }

        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            registerUrls(honeytip, urls);
        }

        return Message.registerPostSuccess(HoneyTip.class, honeytip.getId());
    }

    private void registerImages(final HoneyTip honeytip, final List<MultipartFile> uploadImages) {
        List<FileInput> files = uploadImages.stream()
                .map(MultipartFileAdapter::new)
                .collect(Collectors.toList());
        List<String> uploadUrls = uploader.uploadFiles(files);

        uploadUrls.forEach(uploadUrl -> honeyTipImageService.register(honeytip, uploadUrl));
    }

    private void registerUrls(final HoneyTip honeytip, final List<String> urls) {
        urls.forEach(url -> honeyTipUrlService.register(honeytip, url));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */
    @Transactional
    @FilterBadWord
    public Message edit(final Long postId, final HoneyTipWebEdit request, final Long currentMemberId) {

        // 기존 게시글 찾기
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(postId);
        honeyTipPostService.checkEditPermission(honeyTip, currentMemberId);

        HoneyTipEdit honeyTipEdit = HoneyTipEdit.of(request.getTitle(), request.getContent());
        // title, content 수정
        HoneyTipEditor postEditor = getPostEditor(honeyTipEdit, honeyTip);
        honeyTip.edit(postEditor);

        // url 수정
        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            // URL 리스트 수정
            honeyTipUrlService.updateHoneyTipUrls(honeyTip, urls);
        }

        // 이미지 추가
        List<MultipartFile> addImages = request.getAddImages();
        if (addImages != null && !addImages.isEmpty()) {
            registerImages(honeyTip, addImages);
        }

        // 이미지 삭제
        List<Long> deleteImageIds = request.getDeleteImageIds();
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            deleteImages(deleteImageIds, currentMemberId);
        }

        return Message.editPostSuccess(HoneyTip.class, honeyTip.getId());
    }

    private HoneyTipEditor getPostEditor(final HoneyTipEdit request, final HoneyTip honeyTip) {
        HoneyTipEditor.HoneyTipEditorBuilder editorBuilder = honeyTip.toEditor();
        return editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    private void deleteImages(final List<Long> deleteImageIds, final Long currentMemberId) {
        honeyTipImageService.deleteImages(deleteImageIds, currentMemberId);
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long postId, final Long currentMemberId) {
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(postId);

        honeyTipPostService.checkEditPermission(honeyTip, currentMemberId);
        honeyTip.deactivate();

        return Message.deletePostSuccess(HoneyTip.class, postId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportWebCreate request, final Long reporterId) {
        HoneyTip honeytip = honeyTipPostService.getHoneytip(postId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        Member reporter = memberService.getById(reporterId);
        reportService.reportHoneyTip(create, honeytip, reporter);
        return Message.reportPostSuccess(HoneyTip.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- 단건 READ -------------------------------------------- */
    public HoneyTipSingleResponse getSinglePost(final Long postId, final Long currentMemberId) {
        HoneyTip honeyTipWithImgAndUrl = honeyTipPostService.findHoneyTipWithDetails(postId);
        List<HoneyTipComment> activeComments = honeyTipCommentService.findCommentsByHoneyTipId(postId);

        int likeCount = honeyTipLikeService.countHoneyTipLikes(postId).intValue();
        int scrapCount = honeyTipScrapService.countHoneyTipScraps(postId).intValue();

        boolean likedByCurrentUser = honeyTipLikeService.isHoneyTipLikeExists(postId, currentMemberId);
        boolean scrapedByCurrentUser = honeyTipScrapService.isHoneyTipScrapExists(postId, currentMemberId);

        return HoneyTipSingleResponse.of(honeyTipWithImgAndUrl,
                activeComments, likeCount, scrapCount, likedByCurrentUser, scrapedByCurrentUser);
    }

    /* -------------------------------------------- 단건 READ 끝 -------------------------------------------- */

}
