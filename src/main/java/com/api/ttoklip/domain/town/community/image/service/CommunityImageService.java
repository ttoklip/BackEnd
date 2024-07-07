package com.api.ttoklip.domain.town.community.image.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import com.api.ttoklip.domain.town.community.image.repository.CommunityImageRepository;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityImageService {

    private final CommunityImageRepository communityImageRepository;

    @Transactional
    public void register(final Community community, final String uploadUrl) {
        CommunityImage newCommunityImage = CommunityImage.of(community, uploadUrl);
        communityImageRepository.save(newCommunityImage);
    }

    @Transactional
    public void deleteImages(final List<Long> deleteImageIds) {
        validImagesExists(deleteImageIds);
        communityImageRepository.allImageOwner(deleteImageIds, getCurrentMember().getId());
        communityImageRepository.deleteByImageIds(deleteImageIds);
    }

    // 기존 이미지가 DB에 존재하는 이미지들인지?
    private void validImagesExists(final List<Long> imageIds) {
        boolean allImageIdsExist = communityImageRepository.doAllImageIdsExist(imageIds);
        if (!allImageIdsExist) {
            throw new ApiException(ErrorType.DELETE_INVALID_IMAGE_IDS);
        }
    }
}
