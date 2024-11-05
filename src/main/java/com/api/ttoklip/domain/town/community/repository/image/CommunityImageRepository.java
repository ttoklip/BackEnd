package com.api.ttoklip.domain.town.community.repository.image;

import com.api.ttoklip.domain.town.community.domain.CommunityImage;

import java.util.List;

public interface CommunityImageRepository {
    void allImageOwner(List<Long> imageIds, Long memberId);

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);

    CommunityImage save(CommunityImage newCommunityImage);
}
