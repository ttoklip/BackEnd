package com.api.ttoklip.domain.honeytip.repository.post;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipRepository extends JpaRepository<HoneyTip, Long>, HoneyTipRepositoryCustom {

}
