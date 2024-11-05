package com.api.ttoklip.domain.town.community.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.domain.town.community.repository.post.CommunityJpaRepository;
import com.api.ttoklip.domain.town.community.repository.post.CommunityRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommonService {

    private final S3FileUploader s3FileUploader;
    private final CommunityRepository communityRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Community getCommunity(final Long postId) {
        return communityRepository.findByIdActivated(postId);
    }

    public List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return s3FileUploader.uploadMultipartFiles(uploadImages);
    }

    public void checkEditPermission(final Community community) {
        Long writerId = community.getMember().getId();
        Long currentMemberId = getCurrentMember().getId();

        if (!writerId.equals(currentMemberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */

}
