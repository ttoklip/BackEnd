package com.domain.community.domain;

import com.domain.common.vo.TownCriteria;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityRepository {

    Community findByIdActivated(Long communityId);

    Community findByIdFetchJoin(Long postId);

    List<CommunityComment> findActiveCommentsByCommunityId(Long postId);

    List<Community> getRecent3(TownCriteria townCriteria, String street);

    Page<Community> getPaging(TownCriteria townCriteria, Pageable pageable, String street, String sort);

    Community save(Community community);

    Page<Community> getContain(String keyword, Pageable pageable, String sort);

    Page<Community> getMatchWriterPaging(Long memberId, Pageable pageable);
}
