package com.api.ttoklip.domain.town.community.repository;

import com.api.ttoklip.domain.town.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {

}
