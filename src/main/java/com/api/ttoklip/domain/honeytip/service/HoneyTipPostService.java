package com.api.ttoklip.domain.honeytip.service;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.repository.post.HoneyTipRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
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
public class HoneyTipPostService {

    private final S3FileUploader s3FileUploader;
    private final HoneyTipRepository honeytipRepository;

    public HoneyTip getHoneytip(final Long postId) {
        return honeytipRepository.findByIdActivated(postId);
    }

    public List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return s3FileUploader.uploadMultipartFiles(uploadImages);
    }

    public void checkEditPermission(final HoneyTip honeyTip, final Long currentMemberId) {
        Long writerId = honeyTip.getMember().getId();

        if (!writerId.equals(currentMemberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }
    }

    @Transactional
    public HoneyTip saveHoneyTipPost(final HoneyTip honeyTip) {
        return honeytipRepository.save(honeyTip);
    }

    public HoneyTip findHoneyTipWithDetails(final Long postId) {
        return honeytipRepository.findHoneyTipWithDetails(postId);
    }

    public List<HoneyTip> findHouseworkTips() {
        return honeytipRepository.findHouseworkTips();
    }

    public List<HoneyTip> findRecipeTips() {
        return honeytipRepository.findRecipeTips();
    }

    public List<HoneyTip> findSafeLivingTips() {
        return honeytipRepository.findSafeLivingTips();
    }

    public List<HoneyTip> findWelfarePolicyTips() {
        return honeytipRepository.findWelfarePolicyTips();
    }

    public List<HoneyTip> getPopularityTop5() {
        return honeytipRepository.getPopularityTop5();
    }

    public List<HoneyTip> findRecent3() {
        return honeytipRepository.findRecent3();
    }

    public Page<HoneyTip> matchCategoryPaging(final Category category, final Pageable pageable) {
        return honeytipRepository.matchCategoryPaging(category, pageable);
    }

}
