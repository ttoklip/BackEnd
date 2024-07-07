package com.api.ttoklip.domain.town.community.image.repository;

import java.util.List;
public interface CommunityImageRepositoryCustom {
    void allImageOwner(List<Long> imageIds, Long memberId);

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);
}
