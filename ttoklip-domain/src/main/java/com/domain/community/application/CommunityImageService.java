package com.domain.community.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityImage;
import com.domain.community.domain.CommunityImageRepository;
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
    public void deleteImages(final List<Long> deleteImageIds, final Long memberId) {
        validImagesExists(deleteImageIds);
        communityImageRepository.allImageOwner(deleteImageIds, memberId);
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
