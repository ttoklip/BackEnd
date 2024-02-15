package com.api.ttoklip.domain.honeytip.scrap.repository;

import com.api.ttoklip.domain.honeytip.scrap.domain.HoneyTipScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoneyTipScrapRepository extends JpaRepository<HoneyTipScrap, Long>, HoneyTipScrapRepositoryCustom {

    Optional<HoneyTipScrap> findByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);
    boolean existsByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);

}

