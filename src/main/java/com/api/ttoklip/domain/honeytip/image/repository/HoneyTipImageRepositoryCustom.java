package com.api.ttoklip.domain.honeytip.image.repository;

import java.util.List;
public interface HoneyTipImageRepositoryCustom {

    void allImageOwner(List<Long> imageIds, Long memberId);

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);
}
