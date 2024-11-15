package com.domain.community.domain;

import java.util.List;

public interface CommunityImageRepository {
    void allImageOwner(List<Long> imageIds, Long memberId);

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);

    CommunityImage save(CommunityImage newCommunityImage);
}
