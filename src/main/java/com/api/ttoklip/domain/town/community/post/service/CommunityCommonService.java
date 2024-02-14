package com.api.ttoklip.domain.town.community.post.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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
            // todo 게시글 별 커스텀 에러 수정
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }
    }

    public static Member getCurrentMember() {
        return SecurityUtil.getCurrentMember();
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */

}
