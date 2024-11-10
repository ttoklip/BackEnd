package com.domain.honeytip.domain;

import java.util.List;

public interface HoneyTipImageRepository {

    HoneyTipImage save(HoneyTipImage honeyTipImage);

    boolean existsByHoneyTipIdAndUrl(Long honeyTipId, String url);

    void verifyMemberIsImageOwner(List<Long> imageIds, Long memberId);

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);
}
