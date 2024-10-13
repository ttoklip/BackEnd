package com.api.ttoklip.domain.honeytip.repository.image;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipImage;
import java.util.List;

public interface HoneyTipImageRepository {

    void save(HoneyTipImage honeyTipImage);

    boolean existsByHoneyTipIdAndUrl(Long honeyTipId, String url);

    void verifyMemberIsImageOwner(List<Long> imageIds, Long memberId);

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);
}
