package com.api.ttoklip.domain.honeytip.url.repository;

import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipUrlRepository extends JpaRepository<HoneyTipUrl, Long>, HoneyTipUrlRepositoryCustom {
    List<HoneyTipUrl> findByHoneyTipId(Long honeyTipId);
}
