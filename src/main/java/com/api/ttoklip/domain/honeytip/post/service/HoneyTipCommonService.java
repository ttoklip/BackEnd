package com.api.ttoklip.domain.honeytip.post.service;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.repository.HoneyTipRepository;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipCommonService {

    private final S3FileUploader s3FileUploader;
    private final HoneyTipRepository honeytipRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public HoneyTip getHoneytip(final Long postId) {
        return honeytipRepository.findByIdActivated(postId);
    }

    public List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return s3FileUploader.uploadMultipartFiles(uploadImages);
    }

    public void checkEditPermission(final HoneyTip honeyTip) {
        Long writerId = honeyTip.getMember().getId();
        Long currentMemberId = getCurrentMember().getId();

        if (!writerId.equals(currentMemberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDITOR);
        }
    }

    public static Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */

}
