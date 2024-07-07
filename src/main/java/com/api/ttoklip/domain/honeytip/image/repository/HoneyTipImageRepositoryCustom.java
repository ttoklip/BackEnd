package com.api.ttoklip.domain.honeytip.image.repository;

import java.util.List;
public interface HoneyTipImageRepositoryCustom {

    boolean doAllImageIdsExist(List<Long> imageIds);

    void deleteByImageIds(List<Long> imageIds);
}
